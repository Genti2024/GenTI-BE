package com.gt.genti.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenRefreshRequestDto {
	private String refreshToken;
}
