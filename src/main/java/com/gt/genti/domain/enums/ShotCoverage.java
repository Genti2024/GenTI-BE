package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShotCoverage implements ConvertableEnum {
	UPPER_BODY("UPPER_BODY", "바스트샷(상반신)"),
	KNEE_SHOT("KNEE_SHOT", "니샷(무릎 위)"),
	FULL_BODY("FULL_BODY", "풀샷(전신)"),
	ANY("ANY", "아무거나 상관없어요");

	private final String stringValue;
	private final String response;


	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
