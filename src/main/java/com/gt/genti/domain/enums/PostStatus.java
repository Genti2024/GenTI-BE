package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus implements ConvertableEnum {
	DELETED("DELETED"),
	POSTED("POSTED");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@JsonCreator
	public static PostStatus fromString(String value) {
		return EnumUtil.stringToEnum(PostStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}

}
