package com.gt.genti.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogRequester {
	ANONYMOUS("anonymous"),
	USER("user"),
	CREATOR("creator"),
	ADMIN("admin");

	private final String value;

	@Override
	public String toString() {
		return value;
	}
}
