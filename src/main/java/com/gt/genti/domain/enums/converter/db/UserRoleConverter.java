package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.UserRole;

import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter extends DefaultEnumDBConverter<UserRole> {
	public UserRoleConverter() {
		super(UserRole.class);
	}

}
