package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.ConvertableEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {
	public static <E extends Enum<E>> E stringToEnum(Class<E> enumType, String value) {
		for (E enumValue : enumType.getEnumConstants()) {
			if (((ConvertableEnum)enumValue).getStringValue().equals(value)) {
				return enumValue;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. enum : " + enumType.getName() + " String value : " + value);
	}
}
