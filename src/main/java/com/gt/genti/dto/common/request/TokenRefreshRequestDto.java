package com.gt.genti.dto.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class TokenRefreshRequestDto {
	@NotBlank
	@Schema(name = "refreshToken")
	String refreshToken;
}
