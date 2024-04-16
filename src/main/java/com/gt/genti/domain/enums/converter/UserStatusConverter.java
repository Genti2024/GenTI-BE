package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.UserStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
	@Override
	public String convertToDatabaseColumn(UserStatus attribute) {
		return attribute.getStringValue();
	}

	@Override
	public UserStatus convertToEntityAttribute(String value) {
		for (UserStatus UserStatus : UserStatus.values()) {
			if (UserStatus.getStringValue().equals(value)) {
				return UserStatus;
			}
		}
		throw new RuntimeException("데이터에서 값을 읽어오는데 실패했습니다. " + value);
	}
}
