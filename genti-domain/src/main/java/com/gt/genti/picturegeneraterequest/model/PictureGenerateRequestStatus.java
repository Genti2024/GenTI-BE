package com.gt.genti.picturegeneraterequest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateRequestStatus implements ConvertableEnum {
	CREATED("CREATED"),
	ASSIGNING("ASSIGNING"),
	IN_PROGRESS("IN_PROGRESS"),
	CANCELED("CANCELED"),
	REPORTED("REPORTED"),
	MATCH_TO_ADMIN("MATCH_TO_ADMIN"),
	AWAIT_USER_VERIFICATION("AWAIT_USER_VERIFICATION"),
	COMPLETED("COMPLETED");

	private final String stringValue;

	@Override
	public String getResponse() {
		return stringValue;
	}

	@JsonCreator
	public static PictureGenerateRequestStatus fromString(String value) {
		return EnumUtil.stringToEnum(PictureGenerateRequestStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
