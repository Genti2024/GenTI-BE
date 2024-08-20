package com.gt.genti.auth.dto.response;

import com.gt.genti.constants.JWTConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "[Auth][Anonymous] 소셜 로그인 응답 Dto")
public record OauthJwtResponse(
	@Schema(description = "접두사를 포함한 액세스 토큰")
	String accessToken,
	@Schema(description = "접두사를 포함한 리프레시 토큰")
	String refreshToken,
	@Schema(description = "접두사를 포함한 리프레시 토큰", allowableValues = {"OAUTH_FIRST_JOIN", "ADMIN", "USER"})
	String userRoleString
) {
	public static OauthJwtResponse of(String accessToken, String refreshToken, String userRoleString) {
		return new OauthJwtResponse(JWTConstants.JWT_PREFIX + accessToken, JWTConstants.JWT_PREFIX + refreshToken,
			userRoleString);
	}
}

