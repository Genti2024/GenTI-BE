package com.gt.genti.error;

import lombok.Getter;

@Getter
public class ExpectedException extends RuntimeException {
	private final ErrorCode errorCode;

	public ExpectedException(ErrorCode errorCode, Object... args) {
		super(errorCode.getMessage(args));
		this.errorCode = errorCode;
	}

	public String toString() {
		return """
			HttpStatusCode : [%d]
			ExceptionCode : [%s]
			Message : [%s]
			""".formatted(errorCode.getHttpStatusCode().value(), errorCode.getCode(), this.getMessage());
	}
}
