package com.gt.genti.user.dto.response;

import com.gt.genti.jwt.TokenResponse;

public record SocialLoginResponse(
	Long userId,
	String userName,
	boolean isNewUser,
	TokenResponse token
) {
	public static SocialLoginResponse of(Long userId, String userName, boolean isNewUser, TokenResponse token) {
		return new SocialLoginResponse(userId, userName, isNewUser, TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}
