package com.gt.genti.common.converter;

import com.gt.genti.user.model.UserStatus;

import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter extends DefaultEnumDBConverter<UserStatus> {
	public UserStatusConverter() {
		super(UserStatus.class);
	}
}
