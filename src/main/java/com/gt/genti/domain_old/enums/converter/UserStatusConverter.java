package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.UserStatus;

import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter extends DefaultStringAttributeConverter<UserStatus> {
	public UserStatusConverter() {
		super(UserStatus.class);
	}
}
