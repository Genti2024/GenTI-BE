package com.gt.genti.common.converter;

import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

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
			T enumNullValue = enumClassType.getEnumConstants()[0].getNullValue();
			if (enumNullValue == null) {
				throw ExpectedException.withLogging(ResponseCode.NotNullableEnum, enumClassType.getName());
			} else {
				return enumNullValue.getStringValue();
			}
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumUtil.stringToEnum(getEnumClassType(), dbData);
	}
}
