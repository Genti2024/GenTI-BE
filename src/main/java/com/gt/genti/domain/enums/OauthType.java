package com.gt.genti.domain.enums;

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

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
