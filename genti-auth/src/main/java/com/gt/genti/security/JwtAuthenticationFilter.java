package com.gt.genti.security;

import static com.gt.genti.error.ResponseCode.*;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
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
	private final WhiteListConstants whiteListConstants;


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return PatternMatchUtils.simpleMatch(whiteListConstants.getFilterWhiteArray(), request.getRequestURI());

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
			handleUnExpectedException(request, response, e);
		}
		filterChain.doFilter(request, response);
	}

	private void handleUnExpectedException(HttpServletRequest request, HttpServletResponse response,Exception e){
		request.setAttribute("exception", e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	private void handleExpectedException(HttpServletRequest request, HttpServletResponse response,
		ExpectedException expectedException) throws IOException {
			request.setAttribute("exception", expectedException);
			response.setStatus(expectedException.getResponseCode().getHttpStatusCode().value());
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
		return bearerToken;
	}

}