package com.gt.genti.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "[Auth][Anonymous] 소셜 로그인 응답 Dto")
public record SocialWebLoginResponse(
	@Schema(description = "유저 Db Id", example = "1")
	Long userId,
	@Schema(description = "유저 이름 (애플의 경우에는 공백(\"\"))", example = "서병렬", allowableValues = {"", "서병렬"})
	String userName,
	@Schema(description = "oauth platform 이메일", example = "sbl1998@naver.com")
	String email,
	@Schema(description = "신규가입시 true", example = "true")
	boolean isNewUser,

	@Getter
	OauthJwtResponse token
) {
	public static SocialWebLoginResponse of(Long userId, String userName, String email, boolean isNewUser, OauthJwtResponse oauthJwtResponse) {
		return new SocialWebLoginResponse(userId, userName, email, isNewUser, oauthJwtResponse);
	}
}
