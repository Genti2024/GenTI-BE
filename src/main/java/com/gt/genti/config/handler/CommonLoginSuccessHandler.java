package com.gt.genti.config.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.gt.genti.security.JwtConstants;
import com.gt.genti.security.JwtUtils;
import com.gt.genti.security.PrincipalDetail;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private final JwtUtils jwtUtils;
	@Value("${jwt.redirect-url}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		log.info("--------------------------- CommonLoginSuccessHandler ---------------------------");

		PrincipalDetail principal = (PrincipalDetail)authentication.getPrincipal();
		// SavedRequest savedRequest = requestCache.getRequest(request, response);
		// if (savedRequest != null) {
		// 	response.sendRedirect(savedRequest.getRedirectUrl());
		// } else {
		// 	response.sendRedirect("/");
		// }
		Map<String, Object> responseMap = principal.getMemberInfo();
		String accessToken = jwtUtils.generateToken(responseMap, JwtConstants.ACCESS_EXP_TIME);
		String refreshToken = jwtUtils.generateToken(responseMap, JwtConstants.REFRESH_EXP_TIME);

		refreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

		ResponseCookie cookie = ResponseCookie.from("refresh-token", refreshToken)
			.httpOnly(true) // XSS
			// .secure(true) // 네트워크 감청 방지 HTTPS 적용
			.path("/")
			.maxAge(Duration.ofDays(15))
			.sameSite("None")
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		response.sendRedirect(
			redirectUrl + "?access-token=" + accessToken
		);
	}
}