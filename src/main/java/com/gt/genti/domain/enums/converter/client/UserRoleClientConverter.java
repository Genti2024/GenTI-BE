package com.gt.genti.domain.enums.converter.client;

import org.springframework.stereotype.Component;

import com.gt.genti.domain.enums.UserRole;

@Component
public class UserRoleClientConverter extends DefaultEnumClientConverter<UserRole> {

	public UserRoleClientConverter() {
		super(UserRole.class);
	}
}
