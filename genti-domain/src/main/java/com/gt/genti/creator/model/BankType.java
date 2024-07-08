package com.gt.genti.creator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankType implements ConvertableEnum {
	KB("KB", "국민은행"),
	SHINHAN("SHINHAN", "신한은행"),
	HANA("HANA", "하나은행"),
	WOORI("WOORI", "우리은행"),
	IBK("IBK", "기업은행"),
	SC("SC", "SC제일은행"),
	CITY("CITY", "씨티은행"),
	NH("NH", "농협은행"),
	SH("SH", "수협은행"),
	K("K", "케이뱅크"),
	KAKAOBANK("KAKAOBANK", "카카오뱅크"),
	TOSS("TOSS", "토스뱅크"),
	DAEGU("DAEGU", "대구은행"),
	BUSAN("BUSAN", "부산은행"),
	KYONGNAM("KYONGNAM", "경남은행"),
	KWANGJU("KWANGJU", "광주은행"),
	JEONBUK("JEONBUK", "전북은행"),
	JEJU("JEJU", "제주은행"),
	NONE("NONE", "-");
	private final String stringValue;
	private final String response;
	@JsonCreator
	public static BankType fromString(String value) {
		return EnumUtil.stringToEnum(BankType.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return (E)NONE;
	}
}
