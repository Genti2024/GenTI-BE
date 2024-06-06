package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateResponseStatus implements ConvertableEnum {
	BEFORE_WORK("BEFORE_WORK"),
	SUBMITTED_FIRST("SUBMITTED_FIRST"),
	SUBMITTED_FINAL("SUBMITTED_FINAL"),
	REPORTED("REPORTED"),
	COMPLETED("COMPLETED");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@Override
	public Boolean isNullable() {
		return false;
	}
}