package com.gt.genti.openfeign.apple.dto.response;

import lombok.Getter;

@Getter
public class AppleTokenResponse {
	private String access_token;
	private String expires_in;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private String error;
}

