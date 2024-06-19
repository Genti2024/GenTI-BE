package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex implements ConvertableEnum {
	M("M", "남자"),
	W("W", "여자"),
	NONE("NONE", "미입력");
	private final String stringValue;
	private final String response;


	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
