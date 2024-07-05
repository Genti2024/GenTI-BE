package com.gt.genti.other.security;

import static com.gt.genti.error.ResponseCode.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.config.SecurityConfig;

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
	private final DirectoryTraversalChecker directoryTraversalChecker;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return PatternMatchUtils.simpleMatch(SecurityConfig.COMMON_RESOURCE_AND_ALLOWED_URL, request.getRequestURI());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			authenticateRequest(request);
		} catch (ExpectedException expectedException) {
			handleExpectedException(request, response, expectedException);
		} catch (Exception e) {
			request.setAttribute("exception", e);
		}
		filterChain.doFilter(request, response);
	}

	private void authenticateRequest(HttpServletRequest request) {
		String authHeader = request.getHeader(JwtConstants.JWT_HEADER);
		checkAuthorizationHeader(authHeader);
		String token = jwtTokenProvider.getTokenFromHeader(authHeader);
		Authentication authentication = jwtTokenProvider.getAuthentication(token);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	private void handleExpectedException(HttpServletRequest request, HttpServletResponse response,
		ExpectedException expectedException) throws IOException {
		if (TOKEN_NOT_PROVIDED.equals(expectedException.getResponseCode())) {
			String url = request.getRequestURI();
			if (directoryTraversalChecker.isPreviousAttackedUrl(url)) {
				log.info(url + " 에 대한 허가되지 않은 접근");
				request.setAttribute("exception", expectedException.notLogging());
			} else {
				request.setAttribute("exception", expectedException);
			}
		} else {
			request.setAttribute("exception", expectedException);
		}
	}

	private static void checkAuthorizationHeader(String header) {
		if (header == null) {
			throw ExpectedException.withLogging(TOKEN_NOT_PROVIDED);
		} else if (!header.startsWith(JwtConstants.JWT_PREFIX)) {
			throw ExpectedException.withLogging(INVALID_TOKEN);
		}
	}
}