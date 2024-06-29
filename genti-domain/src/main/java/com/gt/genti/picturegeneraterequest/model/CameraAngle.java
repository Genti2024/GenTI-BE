package com.gt.genti.picturegeneraterequest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraAngle implements ConvertableEnum {
	HIGH("HIGH", "위에서 촬영"),
	MIDDLE("MIDDLE", "눈높이에서 촬영"),
	LOW("LOW", "아래에서 촬영"),
	ANY("ANY", "아무거나 상관없어요");

	private final String stringValue;
	private final String response;

	@JsonCreator
	public static CameraAngle fromString(String value) {
		return EnumUtil.stringToEnum(CameraAngle.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
