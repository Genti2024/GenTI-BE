package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "[Auth][Anonymous] Kakao token", description = "앱에서 카카오서버에서 받은 accesstoken 전달")
public class KakaoAccessTokenDto {
	@NotBlank
	@Schema(description = "accessToken", example = "accessToken")
	private String accessToken;

	@NotBlank
	@Schema(description = "fcm token", example = "FCM 서버로부터 받은 기기의 fcm token")
	String fcmToken;
}
