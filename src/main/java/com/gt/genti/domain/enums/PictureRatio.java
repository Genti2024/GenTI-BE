package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureRatio implements ConvertableEnum {
	RATIO_3_2("RATIO_3_2", "가로3:세로2"),
	NONE("NONE", "NONE"),
	RATIO_2_3("RATIO_2_3", "가로2:세로3");
	private final String stringValue;
	private final String response;

	@Override
	public String getResponse() {
		return this.stringValue;
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}

}
