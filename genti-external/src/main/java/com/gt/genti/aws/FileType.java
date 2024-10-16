package com.gt.genti.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
	CREATED_IMAGE("CREATED_IMAGE"),
	ADMIN_UPLOADED_IMAGE("ADMIN_UPLOADED_IMAGE"),
	USER_UPLOADED_IMAGE("USER_UPLOADED_IMAGE"),
	USER_VERIFICATION_IMAGE("USER_VERIFICATION_IMAGE"),

	DEV_CREATED_IMAGE("DEV/CREATED_IMAGE"),
	DEV_ADMIN_UPLOADED_IMAGE("DEV/ADMIN_UPLOADED_IMAGE"),
	DEV_USER_UPLOADED_IMAGE("DEV/USER_UPLOADED_IMAGE"),
	DEV_USER_VERIFICATION_IMAGE("DEV/USER_VERIFICATION_IMAGE");

	private final String stringValue;
}
