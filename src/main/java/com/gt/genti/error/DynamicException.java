package com.gt.genti.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class DynamicException  extends  RuntimeException{
	private final String errorCode;
	private final HttpStatusCode httpStatusCode;

	public DynamicException(String errorCode, String errorMessage, HttpStatusCode httpStatusCode) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.httpStatusCode = httpStatusCode;
	}
}
