package com.gt.genti.user.dto.response;

import com.gt.genti.jwt.TokenResponse;

public record SocialLoginResponse(
	Long userId,
	String userName,
	TokenResponse token
) {
	public static SocialLoginResponse of(Long userId, String userName, TokenResponse token) {
		return new SocialLoginResponse(userId, userName, TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}
