package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ADMIN("ADMIN"),
	CREATOR("CREATOR"),
	USER("USER");

	private final String role;

}
