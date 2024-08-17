package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] Kakao token", description = "kakao 토큰으로 로그인or회원가입 처리 요청 dto")
public class KakaoAuthorizationCodeDto {
	@NotBlank
	@Schema(example = "rhtodaksgdkdyekemf")
	String authorizationCode;


	public static KakaoAuthorizationCodeDto of(String token) {
		return new KakaoAuthorizationCodeDto(token);
	}
}
