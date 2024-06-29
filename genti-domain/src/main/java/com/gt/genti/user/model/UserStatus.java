package com.gt.genti.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

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
