package com.gt.genti.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gt.genti.constants.JWTConstants;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.security.UserAuthentication;
import com.gt.genti.security.service.UserPrincipalDetailsService;
import com.gt.genti.user.model.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final RedisTemplate<String, String> redisTemplate;
	private final UserPrincipalDetailsService userPrincipalDetailsService;

	@Value("${jwt.secretKey}")
	private String JWT_SECRET;

	@PostConstruct
	protected void init() {
		JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
	}

	public TokenResponse reissuedToken(TokenGenerateCommand command) {
		return TokenResponse.of(
			generateAccessToken(command),
			generateRefreshToken(command));
	}

	public String generateAccessToken(TokenGenerateCommand command) {
		final Date now = new Date();
		final Claims claims = Jwts.claims()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + JWTConstants.ACCESS_TOKEN_EXPIRATION_TIME));

		claims.put(JWTConstants.USER_ID, command.getUserId());
		claims.put(JWTConstants.ROLE, command.getRole());
		claims.put(JWTConstants.TOKEN_TYPE, JWTConstants.ACCESS_TOKEN);

		return JWTConstants.JWT_PREFIX + Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.signWith(getSigningKey())
			.compact();
	}

	public String generateRefreshToken(TokenGenerateCommand command) {
		final Date now = new Date();
		final Claims claims = Jwts.claims()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + JWTConstants.REFRESH_TOKEN_EXPIRATION_TIME));

		String userId = command.getUserId();

		claims.put(JWTConstants.USER_ID, userId);
		claims.put(JWTConstants.ROLE, command.getRole());
		claims.put(JWTConstants.TOKEN_TYPE, JWTConstants.REFRESH_TOKEN);

		String refreshToken = JWTConstants.JWT_PREFIX + Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.signWith(getSigningKey())
			.compact();

		redisTemplate.opsForValue().set(
			String.valueOf(userId),
			refreshToken,
			JWTConstants.REFRESH_TOKEN_EXPIRATION_TIME,
			TimeUnit.MILLISECONDS
		);
		return refreshToken;
	}

	private SecretKey getSigningKey() {
		String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
		return Keys.hmacShaKeyFor(encodedKey.getBytes());
	}

	public Long validateRefreshToken(String refreshToken) {
		validateToken(refreshToken);
		Long userId = getUserFromJwt(refreshToken);
		if (Boolean.TRUE.equals(redisTemplate.hasKey(String.valueOf(userId)))) {
			return userId;
		} else {
			throw ExpectedException.withLogging(ResponseCode.TOKEN_REFRESH_FAILED);
		}
	}

	public void validateToken(String token) {
		try {
			final Claims claims = getBody(token);
			if (claims.get(JWTConstants.TOKEN_TYPE).toString().equals(JWTConstants.ACCESS_TOKEN)) {
				return;
			} else if (claims.get(JWTConstants.TOKEN_TYPE).toString().equals(JWTConstants.REFRESH_TOKEN)) {
				return;
			}
			throw ExpectedException.withLogging(ResponseCode.INVALID_TOKEN);
		} catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | SignatureException e) {
			throw ExpectedException.withLogging(ResponseCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			throw ExpectedException.withLogging(ResponseCode.TOKEN_EXPIRED);
		}
	}

	public void deleteRefreshToken(Long userId) {
		if (Boolean.TRUE.equals(redisTemplate.hasKey(String.valueOf(userId)))) {
			ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
			String refreshToken = valueOperations.get(String.valueOf(userId));
			if(refreshToken == null){
				log.info("refreshToken이 없는데 리턴하는 이상한 상황");
			}
			redisTemplate.delete(refreshToken);
		} else {
			throw ExpectedException.withoutLogging(ResponseCode.TOKEN_REFRESH_FAILED);
			// throw new InternalServerException(DISCORD_LOG_APPENDER);
		}
	}

	private Claims getBody(final String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Long getUserFromJwt(String token) {
		Claims claims = getBody(token);
		return Long.parseLong(claims.get(JWTConstants.USER_ID).toString());
	}

	public Authentication getAuthentication(Long userId) {
		UserPrincipal userDetails = (UserPrincipal)userPrincipalDetailsService.loadUserByUsername(
			String.valueOf(userId));
		return new UserAuthentication(userDetails);
	}

}