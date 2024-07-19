package com.gt.genti.picturegenerateresponse.service.mapper;

import com.gt.genti.common.ConvertableEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateResponseStatusForAdmin implements ConvertableEnum {
	BEFORE_WORK("BEFORE_WORK", "작업 대기"),
	IN_PROGRESS("IN_PROGRESS", "작업 중"),
	COMPLETED("COMPLETED", "작업 완료");

	private final String stringValue;
	private final String response;
	@Override
	public String getResponse() {
		return stringValue;
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
