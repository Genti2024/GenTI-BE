package com.gt.genti.constants;

public class WhiteListConstants {

	public static final String[] FILTER_WHITE_LIST = {
		"/login/oauth2/code/kakao",
		"/login/oauth2/code/google",
		"/oauth/authorize",
		"/v1/login",
		"/login/**",
		"/actuator/health",
		//static
		"/favicon.ico",
		// error
		"/error",
		// swagger
		"/swagger-ui",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	};

	public static final String[] SECURITY_WHITE_LIST = {
		"/azcztzuzaztzozrz",
		"/azcztzuzaztzozrz/**",
		"/v1/login",
		"/login/**",
		"/users/login",
		"/error",
		"/swagger-ui",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/springdoc/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	};

}
