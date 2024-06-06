package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum {
	USER("ROLE_USER", "ROLE_USER", "사용자"),
	MANAGER("ROLE_MANAGER", "ROLE_MANAGER", "매니저"),
	CREATOR("ROLE_CREATOR", "ROLE_CREATOR", "공급자"),
	ADMIN("ROLE_ADMIN", "ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER", "어드민"),
	OAUTH_FIRST_JOIN("ROLE_OAUTH_FIRST_JOIN", "ROLE_OAUTH_FIRST_JOIN,ROLE_USER", "최초가입");

	private final String stringValue;
	private final String roleString;
	private final String response;

	public static String getAllRoles(UserRole userRole) {
		return userRole.getRoleString();
	}

	@Override
	public Boolean isNullable() {
		return false;
	}
}
