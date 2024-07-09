package com.gt.genti.security;

import static com.gt.genti.error.ResponseCode.*;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gt.genti.constants.WhiteListConstants;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.util.HttpRequestUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return PatternMatchUtils.simpleMatch(WhiteListConstants.FILTER_WHITE_LIST, request.getRequestURI());
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws
		ServletException,
		IOException {
		try {
			authenticate(request);
		} catch (ExpectedException expectedException) {
			log.info(
				HttpRequestUtil.getUserIP(Objects.requireNonNull(request)) + "의 " + request.getRequestURI() + " 으로의 접근 "
					+ expectedException.getResponseCode().getErrorCode() + " 예외 발생");
			handleExpectedException(request, response, expectedException);
		} catch (Exception e) {
			log.info(
				HttpRequestUtil.getUserIP(Objects.requireNonNull(request)) + "의 " + request.getRequestURI() + " 으로의 접근 "
					+ e.getClass().getSimpleName() + " 예외 발생");
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		filterChain.doFilter(request, response);
	}

	private void handleExpectedException(HttpServletRequest request, HttpServletResponse response,
		ExpectedException expectedException) throws IOException {
		if (TOKEN_NOT_PROVIDED.equals(expectedException.getResponseCode())) {
			request.setAttribute("exception", expectedException);
			response.setStatus(expectedException.getResponseCode().getHttpStatusCode().value());
		} else {
			request.setAttribute("exception", expectedException);
			response.setStatus(expectedException.getResponseCode().getHttpStatusCode().value());
		}
	}

	private void authenticate(HttpServletRequest request) {
		final String token = getJwtFromRequest(request);
		jwtTokenProvider.validateToken(token);

		Long userId = jwtTokenProvider.getUserFromJwt(token);
		Authentication authentication = jwtTokenProvider.getAuthentication(userId);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (!StringUtils.hasText(bearerToken)){
			throw ExpectedException.withoutLogging(TOKEN_NOT_PROVIDED);
		}
		if(!bearerToken.startsWith("Bearer ")){
			throw ExpectedException.withoutLogging(INVALID_TOKEN);
		}
		return bearerToken.substring("Bearer ".length());
	}

}