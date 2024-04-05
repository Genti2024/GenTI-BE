package com.gt.genti.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
	DELETED("DELETED"),
	POSTED("POSTED");

	private final String status;

}
