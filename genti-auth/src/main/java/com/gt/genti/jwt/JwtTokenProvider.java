package com.gt.genti.jwt;

import static com.gt.genti.constants.JWTConstants.*;
import static com.gt.genti.error.ResponseCode.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.security.UserAuthentication;
import com.gt.genti.security.service.UserPrincipalDetailsService;
import com.gt.genti.user.model.UserPrincipal;
import com.gt.genti.user.model.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
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
			.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME));

		claims.put(USER_ID, command.getUserId());
		claims.put(ROLE, command.getRole());
		claims.put(TOKEN_TYPE, ACCESS_TOKEN);

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.signWith(getSigningKey())
			.compact();
	}

	public String generateRefreshToken(TokenGenerateCommand command) {
		final Date now = new Date();
		final Claims claims = Jwts.claims()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME));

		String userId = command.getUserId();

		claims.put(USER_ID, userId);
		claims.put(ROLE, command.getRole());
		claims.put(TOKEN_TYPE, REFRESH_TOKEN);

		String refreshToken = Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims)
			.signWith(getSigningKey())
			.compact();

		redisTemplate.opsForValue().set(
			String.valueOf(userId),
			refreshToken,
			REFRESH_TOKEN_EXPIRATION_TIME,
			TimeUnit.MILLISECONDS
		);
		return refreshToken;
	}

	private SecretKey getSigningKey() {
		String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
		return Keys.hmacShaKeyFor(encodedKey.getBytes());
	}

	public Long getUserIdFromRefreshToken(String refreshToken) {
		validateToken(refreshToken);
		Long userId = getUserFromJwt(refreshToken);
		String foundRefreshToken = redisTemplate.opsForValue().get(userId.toString());
		if (foundRefreshToken == null) {
			throw ExpectedException.withLogging(ResponseCode.REFRESH_TOKEN_NOT_EXISTS);
		}
		if (!refreshToken.substring(JWT_PREFIX.length()).equals(foundRefreshToken)) {
			throw ExpectedException.withLogging(ResponseCode.REFRESH_TOKEN_INVALID);
		}
		return userId;
	}

	private String removeJwtPrefix(String bearerToken) {
		if (!bearerToken.startsWith(JWT_PREFIX)) {
			throw ExpectedException.withoutLogging(INVALID_TOKEN);
		}
		return bearerToken.substring(JWT_PREFIX.length());
	}

	public void validateToken(String token) {
		try {
			token = removeJwtPrefix(token);
			final Claims claims = getBody(token);
			if (claims.get(TOKEN_TYPE).toString().equals(ACCESS_TOKEN)) {
				return;
			} else if (claims.get(TOKEN_TYPE).toString().equals(REFRESH_TOKEN)) {
				return;
			}
			throw ExpectedException.withLogging(ResponseCode.INVALID_TOKEN);
		} catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | SignatureException e) {
			throw ExpectedException.withLogging(ResponseCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			throw ExpectedException.withLogging(TOKEN_EXPIRED);
		}
	}

	public void deleteRefreshToken(Long userId) {
		if (Boolean.TRUE.equals(redisTemplate.hasKey(String.valueOf(userId)))) {
			String refreshToken = redisTemplate.opsForValue().get(String.valueOf(userId));
			if (refreshToken != null) {
				redisTemplate.delete(refreshToken);
				return;
			}
			throw ExpectedException.withLogging(ResponseCode.REFRESH_TOKEN_NOT_EXISTS);
		}
		throw ExpectedException.withoutLogging(ResponseCode.REFRESH_TOKEN_NOT_EXISTS);
	}

	private Claims getBody(final String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Long getUserFromJwt(String token) {
		token = removeJwtPrefix(token);
		Claims claims = getBody(token);
		return Long.parseLong(claims.get(USER_ID).toString());
	}
	public String getUserRoleStringFromJwt(String token) {
		token = removeJwtPrefix(token);
		Claims claims = getBody(token);
		return claims.get(ROLE).toString();
	}


	public Authentication getAuthentication(Long userId) {
		UserPrincipal userDetails = (UserPrincipal)userPrincipalDetailsService.loadUserByUsername(
			String.valueOf(userId));
		return new UserAuthentication(userDetails);
	}

	public TokenResponse reissueIfValid(@NotBlank String accessToken, @NotBlank String refreshToken) {
		Long userId = getUserIdFromRefreshToken(refreshToken);
		try {
			validateToken(accessToken);
		} catch (ExpectedException e) {
			if (!TOKEN_EXPIRED.equals(e.getResponseCode())) {
				throw e;
			}
		}
		Authentication authentication = getAuthentication(userId);
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		UserRole userRole = userPrincipal.getUserRole();
		TokenGenerateCommand tokenGenerateCommand = TokenGenerateCommand.builder()
			.userId(userId.toString())
			.role(userRole.getAuthority())
			.build();
		String reissuedRefreshToken = generateRefreshToken(tokenGenerateCommand);
		String reissuedAccessToken = generateAccessToken(tokenGenerateCommand);
		return TokenResponse.of(reissuedAccessToken, reissuedRefreshToken);
	}
}