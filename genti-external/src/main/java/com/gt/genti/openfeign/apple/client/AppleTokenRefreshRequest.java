package com.gt.genti.openfeign.apple.client;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppleTokenRefreshRequest {

	private String refresh_token;
	private String client_id;
	private String client_secret;
	private String grant_type;

	public static AppleTokenRefreshRequest of(String refresh_token, String clientId, String clientSecret,
		String grantType) {
		return new AppleTokenRefreshRequest(refresh_token, clientId, clientSecret, grantType);
	}
}
