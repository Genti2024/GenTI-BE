package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShotCoverage implements ConvertableEnum {
	FACE("FACE", "얼굴만 클로즈업"),
	UPPER_BODY("UPPER_BODY", "허리 위로 촬영"),
	KNEE_UP("KNEE_UP", "무릎 위로 촬영"),
	FULL_BODY("FULL_BODY", "전신 촬영"),
	ANY("ANY", "아무거나 상관없어요");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
