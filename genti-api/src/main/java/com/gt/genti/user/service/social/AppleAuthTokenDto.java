package com.gt.genti.user.service.social;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Auth][Anonymous] Apple token", description = "apple 토큰으로 로그인or회원가입 처리 요청 dto")
public class AppleAuthTokenDto {
	@NotBlank
	@Schema(description = "authorization_code", example = "authorization_code")
	String authorizationCode;

	@NotBlank
	@Schema(description = "id_token", example = "id_token")
	String identityToken;
}
