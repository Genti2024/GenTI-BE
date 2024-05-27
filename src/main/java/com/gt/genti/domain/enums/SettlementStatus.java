package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus implements ConvertableEnum {
	WITHDRAWN("WITHDRAWN"),
	CREATED("CREATED"),
	CANCELLED("CANCELLED");

	private final String stringValue;
}
