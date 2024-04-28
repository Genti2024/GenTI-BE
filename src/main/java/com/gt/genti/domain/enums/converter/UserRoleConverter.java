package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.UserRole;

import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter extends DefaultStringAttributeConverter<UserRole> {
	public UserRoleConverter() {
		super(UserRole.class);
	}

	@Override
	public String convertToDatabaseColumn(UserRole enumValue) {
		return enumValue.toString();
	}

	@Override
	public UserRole convertToEntityAttribute(String value) {
		try {
			return UserRole.valueOf(value);
		} catch (Exception e) {
			throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
		}
	}
}
