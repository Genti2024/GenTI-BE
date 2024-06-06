package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraAngle implements ConvertableEnum {
	ABOVE("ABOVE", "위에서 촬영"),
	EYE_LEVEL("EYE_LEVEL", "같은 높이에서 촬영"),
	BELOW("BELOW", "아래에서 촬영"),
	ANY("ANY", "아무거나 상관없어요");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
