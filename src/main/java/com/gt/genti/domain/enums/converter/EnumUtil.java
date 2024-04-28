package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ConvertableEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {
	private static <E extends Enum<E> & ConvertableEnum> E convertNullToEnum(Class<E> enumType) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (enumValue.name().equals("NULL")) {
				return enumValue;
			}
		}
		throw new RuntimeException("""
			enum type : %s은 null 값을 허용하지 않습니다.
			""".formatted(enumType));
	}

	public static <E extends Enum<E> & ConvertableEnum> E stringToEnum(Class<E> enumType, String value) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (enumValue.getStringValue().equals(value)) {
				return enumValue;
			}
		}

		try {
			return convertNullToEnum(enumType);
		} catch (Exception e) {
			throw new RuntimeException("""
				DB -> ENUM 값 불러오기 실패  enum : %s value :  %s \n%s
				""".formatted(enumType.getName(), value, e.getMessage()));
		}
	}
}
