package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface ConvertableEnum {
	@JsonValue // client에게서 enum value로 입력받기위해
	String getStringValue();
}
