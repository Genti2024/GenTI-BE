package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraAngle implements ConvertableEnum {
	ABOVE("위에서 촬영"),
	EYE_LEVEL("같은 높이에서 촬영"),
	BELOW("아래에서 촬영"),
	ANY("아무거나 상관없어요");

	private final String stringValue;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
