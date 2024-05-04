package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus implements ConvertableEnum {
	DELETED("DELETED"),
	POSTED("POSTED");

	private final String stringValue;

}
