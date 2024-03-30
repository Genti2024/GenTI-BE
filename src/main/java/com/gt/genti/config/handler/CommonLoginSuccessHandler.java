package com.gt.genti.config.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.security.controller.JwtConstants;
import com.gt.genti.security.controller.JwtUtils;
import com.gt.genti.security.controller.PrincipalDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		log.info("--------------------------- CommonLoginSuccessHandler ---------------------------");

		PrincipalDetail principal = (PrincipalDetail)authentication.getPrincipal();

		log.info("authentication.getPrincipal() = {}", principal);

		Map<String, Object> responseMap = principal.getMemberInfo();
		responseMap.put("accessToken", JwtUtils.generateToken(responseMap, JwtConstants.ACCESS_EXP_TIME));
		responseMap.put("refreshToken", JwtUtils.generateToken(responseMap, JwtConstants.REFRESH_EXP_TIME));
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(responseMap);

		response.setContentType("application/json; charset=UTF-8");

		PrintWriter writer = response.getWriter();
		writer.println(json);
		writer.flush();
	}
}