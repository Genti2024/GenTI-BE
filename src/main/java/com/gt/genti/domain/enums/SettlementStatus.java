package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus implements ConvertableEnum {
	WITHDRAWN("WITHDRAWN", "출금 완료"),
	CREATED("CREATED", "출금 가능"),
	CANCELLED("CANCELLED", "취소됨");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
