package com.gt.genti.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gt.genti.config.auth.SecurityConfig;
import com.gt.genti.error.CustomJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtVerifyFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	// 상품 이미지가 보이지 않기에 상품 이미지를 출력하는 /api/items/view 경로를 추가

	private static void checkAuthorizationHeader(String header) {
		if (header == null) {
			throw new CustomJwtException("토큰이 전달되지 않았습니다");
		} else if (!header.startsWith(JwtConstants.JWT_PREFIX)) {
			throw new CustomJwtException("BEARER 로 시작하지 않는 올바르지 않은 토큰 형식입니다");
		}
	}

	// 필터를 거치지 않을 URL 을 설정하고, true 를 return 하면 현재 필터를 건너뛰고 다음 필터로 이동
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return PatternMatchUtils.simpleMatch(SecurityConfig.COMMON_RESOURCE_AND_ALLOWED_URL, request.getRequestURI());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("--------------------------- JwtVerifyFilter ---------------------------");

		String authHeader = request.getHeader(JwtConstants.JWT_HEADER);

		try {
			checkAuthorizationHeader(authHeader);   // header 가 올바른 형식인지 체크
			String token = jwtTokenProvider.getTokenFromHeader(authHeader);
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			log.info("authentication = {}", authentication);
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authentication);
			log.info("context : " + context);
			SecurityContextHolder.setContext(context);
		} catch (Exception e) {
			log.info("CustomExpiredJwtException 발생");
			log.info(e.getMessage());
		}

		filterChain.doFilter(request, response);    // 다음 필터로 이동

	}
}