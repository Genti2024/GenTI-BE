package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ConvertableEnum;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultStringAttributeConverter<T extends Enum<T> & ConvertableEnum> implements AttributeConverter<T, String> {
	private final Class<T> enumClassType;
	@Override
	public String convertToDatabaseColumn(T attribute) {
		return attribute.getStringValue();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumUtil.stringToEnum(enumClassType, dbData);
	}
}
