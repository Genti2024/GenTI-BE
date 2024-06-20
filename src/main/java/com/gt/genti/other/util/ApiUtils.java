package com.gt.genti.other.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiUtils {

	public static <T> ResponseEntity<ApiResult<T>> success(T response) {
		return new ResponseEntity<>(new ApiResult<>(true, response), HttpStatus.OK);
	}

	public static ResponseEntity<ApiResult<?>> error(ExpectedException exception) {
		if (exception.shouldLogError()) {
			log.error(exception.toString());
		}
		return new ResponseEntity<>(new ApiResult<>(false, null, exception),
			exception.getErrorCode().getHttpStatusCode());
	}

	@Getter
	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final String errorCode;
		private final String errorMessage;

		public ApiResult(boolean success, T response) {
			this.success = success;
			this.response = response;
			this.errorCode = null;
			this.errorMessage = null;
		}

		public ApiResult(boolean success, T response, ExpectedException exception) {
			this.success = success;
			this.response = response;
			this.errorCode = exception.getErrorCode().getCode();
			this.errorMessage = exception.getMessage();
		}
	}



}