package com.gt.genti.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthPlatform implements ConvertableEnum {
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
	public static OauthPlatform fromString(String value) {
		return EnumUtil.stringToEnum(OauthPlatform.class, value);
	}
	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
