package com.gt.genti.constants;

public class JWTConstants {

	public static final String USER_ID = "userId";
	public static final String TOKEN_TYPE = "type";
	public static final String ACCESS_TOKEN = "access";
	public static final String REFRESH_TOKEN = "refresh";
	public static final String ROLE = "role";
	public static final String JWT_HEADER = "Authorization";
	public static final String JWT_PREFIX = "Bearer ";
	public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 20 * 1000L;
	public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 7 * 2 * 1000L;

}