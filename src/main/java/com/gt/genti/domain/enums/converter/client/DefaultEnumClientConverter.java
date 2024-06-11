package com.gt.genti.domain.enums.converter.client;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import com.gt.genti.domain.enums.ConvertableEnum;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class DefaultEnumClientConverter<T extends Enum<T> & ConvertableEnum>
	implements Converter<String, T> {
	private final Class<T> enumClassType;

	@Override
	public T convert(@NotNull String source) {
		return EnumUtil.stringToEnum(enumClassType, source);
	}

}
