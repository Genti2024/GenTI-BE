package com.gt.genti.common;

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
