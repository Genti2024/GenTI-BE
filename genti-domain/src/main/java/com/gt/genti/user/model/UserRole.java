package com.gt.genti.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gt.genti.common.ConvertableEnum;
import com.gt.genti.common.EnumUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements ConvertableEnum {
	USER("USER", "ROLE_USER", "ROLE_USER", "사용자"),
	MANAGER("MANAGER", "ROLE_MANAGER", "ROLE_MANAGER", "매니저"),
	CREATOR("CREATOR", "ROLE_CREATOR", "ROLE_CREATOR", "공급자"),
	ADMIN("ADMIN", "ROLE_ADMIN", "ROLE_ADMIN,ROLE_MANAGER,ROLE_CREATOR,ROLE_USER", "어드민"),
	OAUTH_FIRST_JOIN("OAUTH_FIRST_JOIN", "ROLE_OAUTH_FIRST_JOIN", "ROLE_OAUTH_FIRST_JOIN,ROLE_USER", "최초가입");

	private final String stringValue;
	private final String authority;
	private final String roles;
	private final String response;

	@JsonCreator
	public static UserRole fromString(String value) {
		return EnumUtil.stringToEnum(UserRole.class, value);
	}

	@Override
	public <E extends Enum<E> & ConvertableEnum> E getNullValue() {
		return null;
	}
}
