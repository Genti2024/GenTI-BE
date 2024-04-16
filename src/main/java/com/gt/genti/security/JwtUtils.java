package com.gt.genti.security;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gt.genti.config.auth.UserDetailsServiceImpl;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.converter.EnumUtil;
import com.gt.genti.domain.enums.converter.UserRoleConverter;
import com.gt.genti.error.CustomJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@Value("${jwt.secretKey}")
	private String secretKey;

	// 헤더에 "Bearer XXX" 형식으로 담겨온 토큰을 추출한다
	public String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}

	public String generateToken(Map<String, Object> valueMap, int validTime) {
		SecretKey key = null;
		try {
			key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return JwtConstants.JWT_PREFIX + Jwts.builder()
			.setHeader(Map.of("typ", "JWT"))
			.setClaims(valueMap)
			.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
			.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validTime).toInstant()))
			.signWith(key)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Map<String, Object> claims = validateToken(token);
		String email = (String)claims.get("email");
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);

		// Set<SimpleGrantedAuthority> authorities = Collections.singleton(
		// 	new SimpleGrantedAuthority(user.getRole()));
		// PrincipalDetail principalDetail = new PrincipalDetail(userDetails, );

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public Map<String, Object> validateToken(String token) {
		Map<String, Object> claim = null;
		try {
			SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
			claim = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
				.getBody();
		} catch (ExpiredJwtException expiredJwtException) {
			throw new CustomExpiredJwtException("토큰이 만료되었습니다", expiredJwtException);
		} catch (Exception e) {
			throw new CustomJwtException("Error");
		}
		return claim;
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