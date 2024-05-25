package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestMatchStrategy implements ConvertableEnum {
	ADMIN_ONLY("ADMIN_ONLY"),
	CREATOR_ADMIN("CREATOR_ADMIN"),
	CREATOR_ONLY("CREATOR_ONLY");

	private final String stringValue;
}
