package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WithdrawRequestStatus implements ConvertableEnum {
	AVAILABLE("AVAILABLE", "출금 가능"),
	IN_PROGRESS("IN_PROGRESS", "출금 진행중"),
	COMPLETED("COMPLETED", "출금 완료"),
	REJECTED("REJECTED", "거절됨");

	private final String stringValue;
	private final String response;

	@Override
	public Boolean isNullable() {
		return false;
	}
}
