package com.gt.genti.jwt;

import com.gt.genti.constants.JWTConstants;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Genti 토큰 응답 Dto")
public record TokenResponse(
	@Schema(description ="액세스 토큰", example = "defaef.32faeE@.szfes!")
	String accessToken,
	@Schema(description ="리프레시 토큰", example = "defaef.32faeE@.szfes!")
	String refreshToken
) {
	public static TokenResponse of(String accessToken, String refreshToken) {
		return new TokenResponse(JWTConstants.JWT_PREFIX + accessToken, JWTConstants.JWT_PREFIX + refreshToken);
	}
}