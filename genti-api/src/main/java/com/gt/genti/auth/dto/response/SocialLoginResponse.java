package com.gt.genti.auth.dto.response;

import com.gt.genti.jwt.TokenResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "[Auth][Anonymous] 소셜 로그인 응답 Dto")
public record SocialLoginResponse(
	@Schema(description = "유저 Db Id", example = "1")
	Long userId,
	@Schema(description = "유저 이름 (애플의 경우에는 공백(\"\"))", example = "서병렬", allowableValues = {"", "서병렬"})
	String userName,
	@Schema(description = "oauth platform 이메일", example = "sbl1998@naver.com")
	String email,
	@Schema(description = "신규가입시 true", example = "true")
	boolean isNewUser,

	TokenResponse token
) {
	public static SocialLoginResponse of(Long userId, String userName, String email, boolean isNewUser, TokenResponse token) {
		return new SocialLoginResponse(userId, userName, email, isNewUser, TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}
