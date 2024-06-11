package com.gt.genti.other.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiUtils {

	public static <T> ResponseEntity<ApiResult<T>> success(T response) {
		return new ResponseEntity<>(new ApiResult<>(true, response), HttpStatus.OK);
	}

	public static ResponseEntity<ApiResult<ExpectedException>> error(ExpectedException exception) {
		if (exception.shouldLogError()) {
			log.error(exception.toString());
		}
		return new ResponseEntity<>(new ApiResult<>(false, null, exception),
			exception.getErrorCode().getHttpStatusCode());
	}

	public static ResponseEntity<ApiResult<String>> error(String error) {
		return new ResponseEntity<>(new ApiResult<>(false, error), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Getter
	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final String errorCode;
		private final String errorMessage;

		private ApiResult(boolean success, T response, ErrorCode domainErrorCode) {
			this.success = success;
			this.response = response;
			this.errorCode = domainErrorCode.getCode();
			this.errorMessage = domainErrorCode.getMessage();
		}

		private ApiResult(boolean success, T response) {
			this.success = success;
			this.response = response;
			this.errorCode = null;
			this.errorMessage = null;
		}

		private ApiResult(boolean success, T response, ExpectedException exception) {
			this.success = success;
			this.response = response;
			this.errorCode = exception.getErrorCode().getCode();
			this.errorMessage = exception.getMessage();
		}
	}
}