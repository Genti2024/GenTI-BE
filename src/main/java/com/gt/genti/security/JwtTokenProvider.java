package com.gt.genti.security;

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

import com.gt.genti.config.auth.UserDetailsServiceImpl;
import com.gt.genti.domain.User;
import com.gt.genti.error.CustomJwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

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
		String id = (String)claims.getSubject();
		List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTH).toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.toList();
		User principal = User.createPrincipalOnlyUser(Long.parseLong(id));
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public Claims validateToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
				.getBody();
		} catch (ExpiredJwtException expiredJwtException) {
			throw new CustomExpiredJwtException("토큰이 만료되었습니다", expiredJwtException);
		} catch (Exception e) {
			throw new CustomJwtException("Error");
		}
	}

	// 토큰이 만료되었는지 판단하는 메서드
	public boolean isExpired(String token) {
		try {
			validateToken(token);
		} catch (Exception e) {
			return (e instanceof CustomExpiredJwtException);
		}
		return false;
	}

	// 토큰의 남은 만료시간 계산
	public long tokenRemainTime(Integer expTime) {
		Date expDate = new Date((long)expTime * (1000));
		long remainMs = expDate.getTime() - System.currentTimeMillis();
		return remainMs / (1000 * 60);
	}
}