package com.gt.genti.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum {
	USER("USER", "ROLE_USER", "사용자"),
	MANAGER("MANAGER", "ROLE_MANAGER", "매니저"),
	CREATOR("CREATOR", "ROLE_CREATOR", "공급자"),
	ADMIN("ADMIN", "ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER", "어드민"),
	OAUTH_FIRST_JOIN("OAUTH_FIRST_JOIN", "ROLE_OAUTH_FIRST_JOIN,ROLE_USER", "최초가입");

	private final String stringValue;
	private final String roleString;
	private final String response;

	public static String getAllRoles(UserRole userRole) {
		return userRole.getRoleString();
	}

	@JsonCreator
	public static UserRole fromString(String value) {
		return EnumUtil.stringToEnum(UserRole.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
