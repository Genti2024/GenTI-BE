package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements ConvertableEnum {

	ACTIVATED("ACTIVATED", "활성"),
	DELETED("DELETED", "삭제"),
	DEACTIVATED("DEACTIVATED", "비활성");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
