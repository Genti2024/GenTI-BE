package com.gt.genti.picturegenerateresponse.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateResponseStatus implements ConvertableEnum {
	BEFORE_WORK("BEFORE_WORK"),
	SUBMITTED_FIRST("SUBMITTED_FIRST"),
	ADMIN_BEFORE_WORK("ADMIN_BEFORE_WORK"),
	ADMIN_IN_PROGRESS("ADMIN_IN_PROGRESS"),
	SUBMITTED_FINAL("SUBMITTED_FINAL"),
	REPORTED("REPORTED"),
	EXPIRED("EXPIRED"),
	COMPLETED("COMPLETED");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@JsonCreator
	public static PictureGenerateResponseStatus fromString(String value) {
		return EnumUtil.stringToEnum(PictureGenerateResponseStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}

}