package com.gt.genti.domain.enums.converter.db;

import org.apache.commons.codec.binary.StringUtils;

import com.gt.genti.domain.enums.ConvertableEnum;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnum(Class<E> enumType, String value) {
		return toEnum(enumType, value, false);
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnumIgnoreCase(Class<E> enumType, String value) {
		return toEnum(enumType, value, true);
	}

	private static <E extends Enum<E> & ConvertableEnum> E toEnum(Class<E> enumType, String value, boolean ignoreCase) {
		if (value == null) {
			E enumNullValue = enumType.getEnumConstants()[0].getNullValue();
			if (enumNullValue == null) {
				throw ExpectedException.withLogging(DefaultErrorCode.NotNullableEnum, enumType);
			} else {
				return enumNullValue;
			}
		}
		for (E enumValue : enumType.getEnumConstants()) {
			if (ignoreCase) {
				if (enumValue.getStringValue().equalsIgnoreCase(value)) {
					return enumValue;
				}
			} else {
				if (StringUtils.equals(value, enumValue.getStringValue())) {
					return enumValue;
				}
			}
		}

		throw ExpectedException.withLogging(DefaultErrorCode.DBToEnumFailed, enumType.getSimpleName(), value);
	}

}
