package com.gt.genti.error;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {

	HttpStatusCode getHttpStatusCode();
	String getCode();
	String getMessage(Object... args);
}
