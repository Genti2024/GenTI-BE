package com.gt.genti.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.WebUtils;

import com.gt.genti.util.HttpRequestUtil;
import com.gt.genti.util.MDCUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MDCFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		HttpServletRequest httpReq = WebUtils.getNativeRequest(request, HttpServletRequest.class);

		MDCUtil.setJsonValue(MDCUtil.REQUEST_URI_MDC, HttpRequestUtil.getRequestUri(Objects.requireNonNull(httpReq)));
		MDCUtil.setJsonValue(MDCUtil.USER_IP_MDC, HttpRequestUtil.getUserIP(Objects.requireNonNull(httpReq)));
		MDCUtil.setJsonValue(MDCUtil.HEADER_MAP_MDC, HttpRequestUtil.getHeaderMap(httpReq));
		MDCUtil.setJsonValue(MDCUtil.USER_REQUEST_COOKIES, HttpRequestUtil.getUserCookies(httpReq));
		MDCUtil.setJsonValue(MDCUtil.PARAMETER_MAP_MDC, HttpRequestUtil.getParamMap(httpReq));
		MDCUtil.setJsonValue(MDCUtil.BODY_MDC, HttpRequestUtil.getBody(httpReq));
		MDCUtil.set(MDCUtil.USER_REQUEST_ORIGIN, HttpRequestUtil.getOrigin(request));

		try {
			filterChain.doFilter(request, response);
		} catch (MethodArgumentTypeMismatchException exception) {
			request.setAttribute("exception", exception);
		}

	}
}
