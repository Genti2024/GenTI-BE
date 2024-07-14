package com.gt.genti.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogAction {
	SEARCH("search"),
	COMPLETE("complete"),
	VIEW("view"),
	REQUEST("request"),
	GET("get"),
	LOGIN("login"),
	CREATE("create"),
	UPDATE("update"),
	DELETE("delete"),
	OTHER("other"); // 이걸 쓸 때가 오면 그때 새로 이름 지어주세요
	private final String value;

	@Override
	public String toString() {
		return value;
	}
}
