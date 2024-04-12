package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

	ACTIVATED("ACTIVATED"),
	DEACTIVATED("DEACTIVATED");

	private final String status;

}
