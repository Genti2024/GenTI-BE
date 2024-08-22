package com.gt.genti.firebase.dto.request;

public record FcmTokenSaveOrUpdateRequestDto(String token, Long userId) {
	public static FcmTokenSaveOrUpdateRequestDto of(String token, Long userId) {
		return new FcmTokenSaveOrUpdateRequestDto(token, userId);
	}
}