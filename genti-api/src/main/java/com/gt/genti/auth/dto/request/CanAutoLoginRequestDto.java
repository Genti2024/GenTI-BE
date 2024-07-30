package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(name = "[Auth][Anonymous] 자동로그인이 가능한지 여부를 응답받기 위해 토큰을 전달하는 요청 Dto", description = "토큰 전달")
public class CanAutoLoginRequestDto {
	@NotBlank
	@Schema(description = "accessToken")
	String accessToken;

	@NotBlank
	@Schema(description = "refreshToken")
	String refreshToken;
}
