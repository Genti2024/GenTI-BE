package com.gt.genti.domain.enums.converter.db;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;

import com.gt.genti.domain.enums.ConvertableEnum;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {
	public static final String NULL = "NONE";

	private static <E extends Enum<E> & ConvertableEnum> E convertNullToEnum(Class<E> enumType) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (enumValue.name().equals(NULL)) {
				return enumValue;
			}
		}
		throw ExpectedException.withLogging(DefaultErrorCode.NotNullableEnum, enumType);
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnum(Class<E> enumType, String value) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (StringUtils.equals(value, enumValue.getStringValue())) {
				return enumValue;
			}
		}

		try {
			return convertNullToEnum(enumType);
		} catch (Exception e) {
			throw ExpectedException.withLogging(DefaultErrorCode.DBToEnumFailed, enumType.getName(), value,
				e.getMessage());
		}
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnumIgnoreCase(Class<E> enumType, String value) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (enumValue.getStringValue().equalsIgnoreCase(value)) {
				return enumValue;
			}
		}

		try {
			return convertNullToEnum(enumType);
		} catch (Exception e) {
			throw ExpectedException.withLogging(DefaultErrorCode.DBToEnumFailed,
				List.of(enumType.getName(), value, e.getMessage()));
		}
	}

	public static <E extends Enum<E>> boolean validateInputString(Class<E> enumType, String inputValue) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (enumValue.name().equals(inputValue)) {
				return true;
			}
		}
		return false;
	}
}
