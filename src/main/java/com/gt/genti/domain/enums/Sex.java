package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex implements ConvertableEnum {
	M("M"),
	W("W"),
	NONE("NONE");
	private final String stringValue;

	@Override
	public Boolean isNullable() {
		return true;
	}
}
