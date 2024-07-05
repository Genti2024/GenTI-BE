package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex implements ConvertableEnum {
	M("M", "남"),
	W("W", "여"),
	NONE("NONE", "미입력");
	private final String stringValue;
	private final String response;

	@JsonCreator
	public static Sex fromString(String value) {
		return EnumUtil.stringToEnum(Sex.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
