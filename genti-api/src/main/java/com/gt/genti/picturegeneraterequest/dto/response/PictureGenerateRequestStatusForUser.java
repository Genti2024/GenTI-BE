package com.gt.genti.picturegeneraterequest.dto.response;

import com.gt.genti.common.ConvertableEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateRequestStatusForUser implements ConvertableEnum {
	IN_PROGRESS("IN_PROGRESS"),
	AWAIT_USER_VERIFICATION("AWAIT_USER_VERIFICATION"),
	CANCELED("CANCELED"),
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
