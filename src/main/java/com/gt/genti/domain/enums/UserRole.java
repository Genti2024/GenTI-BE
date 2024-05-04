package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum {
	USER("ROLE_USER"),
	MANAGER("ROLE_MANAGER"),
	CREATOR("ROLE_CREATOR"),
	ADMIN("ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER"),
	OAUTH_FIRST_JOIN("ROLE_OAUTH_FIRST_JOIN");

	private final String stringValue;

	public static String getAllRoles(UserRole userRole) {
		return userRole.getStringValue();
	}

	public static String addRole(UserRole role, String roleToAdd) {
		return role.getStringValue() + "," + roleToAdd;
	}

	public static String addRole(String roleToAdd, UserRole role) {
		return role.getStringValue() + "," + roleToAdd;
	}
}
