package com.gt.genti.matchingstrategy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

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
