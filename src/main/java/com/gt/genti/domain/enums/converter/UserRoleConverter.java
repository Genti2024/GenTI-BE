package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.UserRole;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
	@Override
	public String convertToDatabaseColumn(UserRole enumValue) {
		return enumValue.getStringValue();
	}

	@Override
	public UserRole convertToEntityAttribute(String value) {
		for (UserRole userRole : UserRole.values()) {
			if (userRole.getStringValue().equals(value)) {
				return userRole;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}
