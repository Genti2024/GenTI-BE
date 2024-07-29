package com.gt.genti.auth.dto.request;

import com.gt.genti.user.model.OauthPlatform;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] oauth 토큰으로 로그인or회원가입 처리 요청 dto", description = "Oauth 토큰 및 플랫폼")
public class SocialAppLoginRequest {
	@NotBlank
	@Schema(example = "rhtodaksgdkdyekemf")
	String token;

	@NotNull
	@Schema(example = "KAKAO")
	OauthPlatform oauthPlatform;
}
