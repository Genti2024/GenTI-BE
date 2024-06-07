package com.gt.genti.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenRefreshRequestDto {
	private String refreshToken;
}
