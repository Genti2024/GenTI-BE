package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportStatus implements ConvertableEnum {
	NOT_RESOLVED("NOT_RESOLVED"),
	RESOLVED("RESOLVED");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}

}