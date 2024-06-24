package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface ConvertableEnum {

	String getStringValue();

	@JsonValue
	String getResponse();


	<E extends Enum<E> & ConvertableEnum> E getNullValue();
}
