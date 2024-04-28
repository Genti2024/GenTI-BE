package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ConvertableEnum;

public class IgnoreCaseStringAttributeConverter<T extends Enum<T> & ConvertableEnum>
	extends DefaultStringAttributeConverter<T> {
	public IgnoreCaseStringAttributeConverter(Class<T> enumClassType) {
		super(enumClassType);
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		return attribute.getStringValue().toUpperCase();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumUtil.stringToEnumIgnoreCase(getEnumClassType(), dbData);
	}

}
