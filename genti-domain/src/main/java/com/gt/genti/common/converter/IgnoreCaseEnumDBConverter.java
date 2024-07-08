package com.gt.genti.common.converter;

import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;
import com.gt.genti.common.converter.DefaultEnumDBConverter;

public class IgnoreCaseEnumDBConverter<T extends Enum<T> & ConvertableEnum>
	extends DefaultEnumDBConverter<T> {
	public IgnoreCaseEnumDBConverter(Class<T> enumClassType) {
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
