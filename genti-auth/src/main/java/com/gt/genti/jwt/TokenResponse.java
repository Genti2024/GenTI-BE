package com.gt.genti.jwt;

import com.gt.genti.constants.JWTConstants;

public record TokenResponse(
	String accessToken,
	String refreshToken
) {
	public static TokenResponse of(String accessToken, String refreshToken) {
		return new TokenResponse(JWTConstants.JWT_PREFIX + accessToken, JWTConstants.JWT_PREFIX + refreshToken);
	}
}