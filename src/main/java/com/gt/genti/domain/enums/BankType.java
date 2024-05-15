package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankType implements ConvertableEnum {
	KB("국민은행"),
	SHINHAN("신한은행"),
	HANA("하나은행"),
	WOORI("우리은행"),
	IBK("기업은행"),
	SC("SC제일은행"),
	CITY("씨티은행"),
	NH("농협은행"),
	SH("수협은행"),

	K("케이뱅크"),
	KAKAOBANK("카카오뱅크"),
	TOSS("토스뱅크"),

	DAEGU("대구은행"),
	BUSAN("부산은행"),
	KYONGNAM("경남은행"),
	KWANGJU("광주은행"),
	JEONBUK("전북은행"),
	JEJU("제주은행"),
	NONE("NONE");
	private final String stringValue;
}
