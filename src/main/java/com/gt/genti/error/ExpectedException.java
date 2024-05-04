package com.gt.genti.error;

import lombok.Getter;

@Getter
public class ExpectedException extends RuntimeException {
	private final ErrorCode errorCode;

	public ExpectedException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
