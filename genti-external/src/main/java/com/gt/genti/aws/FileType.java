package com.gt.genti.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
	CREATED_IMAGE("CREATED_IMAGE"),
	ADMIN_UPLOADED_IMAGE("ADMIN_UPLOADED_IMAGE"),
	USER_UPLOADED_IMAGE("USER_UPLOADED_IMAGE");

	private final String stringValue;
}
