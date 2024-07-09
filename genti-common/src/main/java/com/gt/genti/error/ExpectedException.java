package com.gt.genti.error;

import lombok.Getter;

@Getter
public class ExpectedException extends RuntimeException {
	private final ResponseCode responseCode;
	private boolean logRequired;

	public static ExpectedException withLogging(ResponseCode responseCode, Object... args) {
		return new ExpectedException(responseCode, true, args);
	}

	public static ExpectedException withoutLogging(ResponseCode responseCode, Object... args) {
		return new ExpectedException(responseCode, false, args);
	}

	private ExpectedException(ResponseCode responseCode, boolean logRequired, Object... args) {
		super(responseCode.getErrorMessage(args));
		this.responseCode = responseCode;
		this.logRequired = logRequired;
	}

	// public String toString() {
	// 	return """
	// 		HttpStatusCode : [%d]
	// 		ExceptionCode : [%s]
	// 		Message : [%s]
	// 		""".formatted(responseCode.getHttpStatusCode().value(), responseCode.getCode(), this.getMessage());
	// }

	public boolean shouldLogError() {
		return this.logRequired;
	}

	public ExpectedException notLogging(){
		this.logRequired = false;
		return this;
	}
}
