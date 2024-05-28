package com.gt.genti.domain.enums.converter;

import com.gt.genti.domain.enums.UserRole;

import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter extends DefaultStringAttributeConverter<UserRole> {
	public UserRoleConverter() {
		super(UserRole.class);
	}

}
