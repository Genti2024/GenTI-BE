package com.gt.genti.domain.enums;

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
	COMPLETED("COMPLETED");

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
