package com.gt.genti.openfeign.apple.dto.response;

import lombok.Data;

@Data
public class AppleTokenRefreshResponse {
	private String access_token;
	private String expires_in;
	private String token_type;
	private String error;
}
