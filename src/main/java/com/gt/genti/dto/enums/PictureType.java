package com.gt.genti.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureType {
	CREATED("CREATED"),
	USER_UPLOAD("USER_UPLOAD");

	private final String stringValue;
}
