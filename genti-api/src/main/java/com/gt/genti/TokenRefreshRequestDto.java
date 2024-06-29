package com.gt.genti;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "인증 토큰 Refresh 요청 Dto")
public class TokenRefreshRequestDto {
	@NotBlank
	@Schema(description = "refreshToken")
	String refreshToken;
}
