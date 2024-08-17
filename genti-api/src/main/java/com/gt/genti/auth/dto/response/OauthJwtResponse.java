package com.gt.genti.auth.dto.response;

import com.gt.genti.constants.JWTConstants;

public record OauthJwtResponse(
	String accessToken,
	String refreshToken,
	String userRoleString
) {
	public static OauthJwtResponse of(String accessToken, String refreshToken, String userRoleString) {
		return new OauthJwtResponse(JWTConstants.JWT_PREFIX + accessToken, JWTConstants.JWT_PREFIX + refreshToken,
			userRoleString);
	}
}
