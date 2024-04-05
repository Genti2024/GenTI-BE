package com.gt.genti.config.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonLoginFailHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		log.info("--------------------------- CommonLoginFailHandler ---------------------------");

		ObjectMapper om = new ObjectMapper();

		String json = om.writeValueAsString(Map.of("error", "Login Fail"));

		response.setContentType("application/json; charset=UTF-8");

		PrintWriter printWriter = response.getWriter();
		printWriter.println(json);
		printWriter.close();
	}
}