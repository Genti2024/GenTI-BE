package com.gt.genti.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "[Auth][Anonymous] 소셜로그인 uri 응답 Dto")
public record AuthUriResponseDto(
	@Schema(description = "접두사를 포함한 액세스 토큰")
	String oauthPlatform,
	@Schema(description = "접두사를 포함한 액세스 토큰")
	String uri
) {

	public static AuthUriResponseDto of(String oauthPlatForm, String uri) {
		return new AuthUriResponseDto(oauthPlatForm, uri);
	}
}
