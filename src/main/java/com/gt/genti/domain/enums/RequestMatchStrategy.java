package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestMatchStrategy implements ConvertableEnum {
	ADMIN_ONLY("ADMIN_ONLY"),
	CREATOR_ADMIN("CREATOR_ADMIN"),
	CREATOR_ONLY("CREATOR_ONLY");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@JsonCreator
	public static RequestMatchStrategy fromString(String value) {
		return EnumUtil.stringToEnum(RequestMatchStrategy.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}

}
