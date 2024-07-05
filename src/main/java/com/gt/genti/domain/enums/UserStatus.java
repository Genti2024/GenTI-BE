package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements ConvertableEnum {

	ACTIVATED("ACTIVATED", "활성"),
	DELETED("DELETED", "삭제"),
	DEACTIVATED("DEACTIVATED", "비활성");

	private final String stringValue;
	private final String response;



	@JsonCreator
	public static UserStatus fromString(String value) {
		return EnumUtil.stringToEnum(UserStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
