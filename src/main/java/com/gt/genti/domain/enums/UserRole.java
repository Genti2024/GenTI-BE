package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum {
	USER("ROLE_USER", "ROLE_USER"),
	MANAGER("ROLE_MANAGER", "ROLE_MANAGER"),
	CREATOR("ROLE_CREATOR", "ROLE_CREATOR"),
	ADMIN("ROLE_ADMIN", "ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER"),
	OAUTH_FIRST_JOIN("ROLE_OAUTH_FIRST_JOIN", "ROLE_OAUTH_FIRST_JOIN,ROLE_USER");

	private final String stringValue;
	private final String roleString;

	public static String getAllRoles(UserRole userRole) {
		return userRole.getRoleString();
	}

	public static String addRole(UserRole role, String roleToAdd) {
		return role.getRoleString() + "," + roleToAdd;
	}

	public static String addRole(String roleToAdd, UserRole role) {
		return role.getRoleString() + "," + roleToAdd;
	}
}
