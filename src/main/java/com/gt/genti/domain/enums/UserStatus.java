package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements ConvertableEnum{

	ACTIVATED("ACTIVATED"),
	DEACTIVATED("DEACTIVATED");

	private final String stringValue;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
