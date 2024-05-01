package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestStatus implements ConvertableEnum{
	BEFORE_WORK("BEFORE_WORK"),
	IN_PROGRESS("IN_PROGRESS"),
	COMPLETED("COMPLETED"),
	CANCELED("CANCELED");

	private final String stringValue;
}
