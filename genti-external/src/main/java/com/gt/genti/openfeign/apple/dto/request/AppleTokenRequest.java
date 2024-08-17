package com.gt.genti.openfeign.apple.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppleTokenRequest {
	private String code;
	private String client_id;
	private String client_secret;
	private String grant_type;

	public static AppleTokenRequest of(String code, String clientId, String clientSecret, String grantType) {
		return new AppleTokenRequest(code, clientId, clientSecret, grantType);
	}
}