package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CameraAngle implements ConvertableEnum {
	HIGH("HIGH", "하이"),
	MIDDLE("MIDDLE", "미들"),
	LOW("LOW", "로우"),
	ANY("ANY", "아무거나 상관없어요");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
