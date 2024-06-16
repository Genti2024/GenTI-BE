package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus implements ConvertableEnum {
	WITHDRAWN_COMPLETED("WITHDRAWN_COMPLETED", "출금 완료"),
	CREATED("CREATED", "출금 가능"),
	WITHDRAWN_IN_PROGRESS("WITHDRAWN_IN_PROGRESS", "출금 진행 중"),
	CANCELLED("CANCELLED", "취소됨");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
