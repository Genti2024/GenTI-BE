package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.UserStatus;

import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter extends DefaultEnumDBConverter<UserStatus> {
	public UserStatusConverter() {
		super(UserStatus.class);
	}
}
