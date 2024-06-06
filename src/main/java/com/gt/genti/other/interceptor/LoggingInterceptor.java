package com.gt.genti.other.interceptor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gt.genti.other.auth.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String requestUrl = request.getRequestURL().toString();
		String method = request.getMethod();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String logMessage;

		if (authentication != null && authentication.isAuthenticated() &&
			!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			logMessage = String.format(
				"\n인증된 접근 - userId : [%d] email : [%s]\nmethod: [%s] uri: %s",
				userDetails.getId(), userDetails.getEmail(), method, requestUrl
			);
		} else {
			logMessage = String.format(
				"\n공개된 resource 접근\nmethod: [%s] uri: %s", method, requestUrl
			);
		}
		log.info(logMessage);
		return true;
	}
}
