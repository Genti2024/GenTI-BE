package com.gt.genti.other.security;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.gt.genti.domain.User;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.auth.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	public static final String AUTH = "auth";
	public static final String ID = "sub";
	public static final String TOKEN_TYPE = "typ";
	public static final String JWT = "JWT";

	@Value("${jwt.secretKey}")
	private String secretKey;

	private SecretKey key;

	@PostConstruct
	void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	// 헤더에 "Bearer XXX" 형식으로 담겨온 토큰을 추출한다
	public String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}

	public String generateToken(Map<String, Object> claims, int validTime) {
		String authorities = (String)claims.get(AUTH);
		return JwtConstants.JWT_PREFIX + Jwts.builder()
			.setHeader(Map.of(TOKEN_TYPE, "JWT"))
			.setSubject((String)claims.get(ID))
			.claim(AUTH, authorities)
			.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
			.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public String generateToken(Authentication authentication, int validTime) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining());

		return JwtConstants.JWT_PREFIX + Jwts.builder()
			.setHeader(Map.of(TOKEN_TYPE, JWT))
			.setSubject(authentication.getName())
			.claim(AUTH, authorities)
			.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
			.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = validateToken(token);
		String id = claims.getSubject();
		String roles = claims.get(AUTH).toString();
		List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();
		UserDetailsImpl userDetails = UserDetailsImpl.builder()
			.user(User.createPrincipalOnlyUser(Long.parseLong(id)))
			.roles(roles).build();
		log.info("""
			요청한 유저 id : [%d]""".formatted(userDetails.getId()));
		return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
	}

	public Claims validateToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
				.getBody();
		} catch (ExpiredJwtException expiredJwtException) {
			throw new ExpectedException(ErrorCode.TOKEN_EXPIRED);
		} catch (Exception e) {
			throw new ExpectedException(ErrorCode.INVALID_TOKEN);
		}
	}

	// 토큰이 만료되었는지 판단하는 메서드
	public boolean isExpired(String token) {
		validateToken(token);
		return true;
	}

	// 토큰의 남은 만료시간 계산
	public long tokenRemainTime(Integer expTime) {
		Date expDate = new Date((long)expTime * (1000));
		long remainMs = expDate.getTime() - System.currentTimeMillis();
		return remainMs / (1000 * 60);
	}
}