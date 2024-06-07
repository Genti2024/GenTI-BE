package com.gt.genti.dto.common.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenRefreshRequestDto {
	@NotBlank
	private String refreshToken;
}
