package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum{

	ADMIN("ADMIN"),
	CREATOR("CREATOR"),
	USER("USER");

	private final String stringValue;
	public String getValueWithRole_Prefix(){
		return "ROLE_" + getStringValue();
	}
}
