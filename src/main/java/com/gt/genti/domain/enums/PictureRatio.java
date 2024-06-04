package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureRatio implements ConvertableEnum {
	THREE_TWO("3:2"),
	TWO_THREE("2:3");
	private final String stringValue;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
