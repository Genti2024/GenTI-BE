package com.gt.genti.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportStatus implements ConvertableEnum {
	NOT_RESOLVED("NOT_RESOLVED", "해결 전"),
	RESOLVED("RESOLVED", "해결 완료");

	private final String stringValue;
	private final String response;
	@JsonCreator
	public static ReportStatus fromString(String value) {
		return EnumUtil.stringToEnum(ReportStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}

}