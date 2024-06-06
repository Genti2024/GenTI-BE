package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface ConvertableEnum {
	@JsonValue
	String getStringValue();

	String getResponse();
	Boolean isNullable();
}
