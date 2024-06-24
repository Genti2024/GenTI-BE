package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthType implements ConvertableEnum {
	GOOGLE("GOOGLE"),
	KAKAO("KAKAO"),
	APPLE("APPLE"),
	NONE("NONE");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@JsonCreator
	public static OauthType fromString(String value) {
		return EnumUtil.stringToEnum(OauthType.class, value);
	}
	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
