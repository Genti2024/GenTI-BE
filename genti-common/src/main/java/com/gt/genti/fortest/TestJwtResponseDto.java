package com.gt.genti.fortest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestJwtResponseDto {
	String accessToken;
	String refreshToken;

	@Builder
	public TestJwtResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
