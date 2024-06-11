package com.gt.genti.error;

import lombok.Getter;

@Getter
public class ExpectedException extends RuntimeException {
	private final ErrorCode errorCode;
	private boolean logRequired;

	public static ExpectedException withLogging(ErrorCode errorCode, Object... args) {
		return new ExpectedException(errorCode, true, args);
	}

	public static ExpectedException withoutLogging(ErrorCode errorCode, Object... args) {
		return new ExpectedException(errorCode, false, args);
	}

	private ExpectedException(ErrorCode errorCode, boolean logRequired, Object... args) {
		super(errorCode.getMessage(args));
		this.errorCode = errorCode;
		this.logRequired = logRequired;
	}

	public String toString() {
		return """
			HttpStatusCode : [%d]
			ExceptionCode : [%s]
			Message : [%s]
			""".formatted(errorCode.getHttpStatusCode().value(), errorCode.getCode(), this.getMessage());
	}

	public boolean shouldLogError() {
		return this.logRequired;
	}

	public ExpectedException notLogging(){
		this.logRequired = false;
		return this;
	}
}
