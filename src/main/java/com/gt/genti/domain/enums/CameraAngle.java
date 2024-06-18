package com.gt.genti.domain.enums;

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

	@Override
	public Boolean isNullable() {
		return false;
	}
}
