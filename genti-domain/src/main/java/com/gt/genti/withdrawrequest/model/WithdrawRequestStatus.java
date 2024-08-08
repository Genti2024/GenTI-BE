package com.gt.genti.withdrawrequest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

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

	@JsonCreator
	public static WithdrawRequestStatus fromString(String value) {
		return EnumUtil.stringToEnum(WithdrawRequestStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
