package com.gt.genti.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(name = "[Auth][Anonymous] Genti 인증토큰 Refresh 요청 Dto", description = "토큰 전달")
public class TokenRefreshRequestDto {
	@NotBlank
	@Schema(description = "refreshToken")
	String accessToken;

	@NotBlank
	@Schema(description = "refreshToken")
	String refreshToken;
}
