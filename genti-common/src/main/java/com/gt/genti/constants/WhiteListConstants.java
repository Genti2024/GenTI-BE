package com.gt.genti.constants;

public class WhiteListConstants {

	public static final String[] FILTER_WHITE_LIST = {
		"/login/oauth2/code/kakao",
		"/login/oauth2/code/google",
		"/oauth/authorize",
		"/**",
		"/v1/user/login",
		"/login",
		"/login/**",
		"/actuator/health",
		"/v1/user/login",
		"/users/login",
		"/test/**",
		"/error",
		"/springdoc/**",
		"/swagger-ui",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	};

	public static final String[] SECURITY_WHITE_LIST = {
		"/login/**",
		"/actuator/health",
		"/v1/user/login",
		"/users/login",
		"/test/**",
		"/error",
		"/springdoc/**",
		"/api-docs/**",
		"/v3/api-docs/**",
		"/h2-console/**",
		"/h2-console"
	};

}
