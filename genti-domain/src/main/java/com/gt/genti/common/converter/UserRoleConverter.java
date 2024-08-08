package com.gt.genti.common.converter;

import com.gt.genti.user.model.UserRole;

import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter extends DefaultEnumDBConverter<UserRole> {
	public UserRoleConverter() {
		super(UserRole.class);
	}

}
