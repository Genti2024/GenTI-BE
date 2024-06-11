package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.ConvertableEnum;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.ExpectedException;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultEnumDBConverter<T extends Enum<T> & ConvertableEnum>
	implements AttributeConverter<T, String> {
	private final Class<T> enumClassType;

	@Override
	public String convertToDatabaseColumn(T attribute) {
		try {
			return attribute.getStringValue();
		} catch (NullPointerException e) {
			if (enumClassType.getEnumConstants()[0].isNullable()) {
				return null;
			} else {
				throw ExpectedException.withLogging(DefaultErrorCode.NotNullableEnum, enumClassType.getName());
			}
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumUtil.stringToEnum(getEnumClassType(), dbData);
	}
}
