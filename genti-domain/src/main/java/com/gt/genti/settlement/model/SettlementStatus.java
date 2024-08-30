package com.gt.genti.settlement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementStatus implements ConvertableEnum {
	CASHOUT_COMPLETED("CASHOUT_COMPLETED", "출금 완료"),
	CREATED("CREATED", "출금 가능"),
	CASHOUT_IN_PROGRESS("CASHOUT_IN_PROGRESS", "출금 진행 중"),
	CANCELLED("CANCELLED", "취소됨");

	private final String stringValue;
	private final String response;

	@JsonCreator
	public static SettlementStatus fromString(String value) {
		return EnumUtil.stringToEnum(SettlementStatus.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
