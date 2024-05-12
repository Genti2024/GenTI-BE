package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PictureGenerateRequestStatus implements ConvertableEnum {
	ASSIGNING("ASSIGNING"),
	BEFORE_WORK("BEFORE_WORK"),
	IN_PROGRESS("IN_PROGRESS"),
	CANCELED("CANCELED"),
	REPORTED("REPORTED"),
	COMPLETED("COMPLETED");

	private final String stringValue;
}
