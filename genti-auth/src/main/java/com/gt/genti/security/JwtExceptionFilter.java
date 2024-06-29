package com.gt.genti.security;

import static com.gt.genti.error.ResponseCode.*;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.error.ExpectedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		try {
			filterChain.doFilter(request, response);
		} catch (ExpectedException expectedException) {
			handleExpectedException(request, response, expectedException);
		} catch (Exception e) {
			request.setAttribute("exception", e);
		}
		filterChain.doFilter(request, response);
	}

	private void handleExpectedException(HttpServletRequest request, HttpServletResponse response,
		ExpectedException expectedException) throws IOException {
		if (TOKEN_NOT_PROVIDED.equals(expectedException.getResponseCode())) {
			request.setAttribute("exception", expectedException.notLogging());
		} else {
			request.setAttribute("exception", expectedException);
		}
	}
}
