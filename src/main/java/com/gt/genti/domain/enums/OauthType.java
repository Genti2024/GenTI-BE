package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OauthType implements com.gt.genti.domain.enums.ConvertableEnum {
	GOOGLE("GOOGLE"),
	KAKAO("KAKAO"),
	APPLE("APPLE"),
	NULL("NULL");

	private final String stringValue;
}
