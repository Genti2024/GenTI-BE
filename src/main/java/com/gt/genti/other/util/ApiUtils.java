package com.gt.genti.other.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gt.genti.error.ErrorCode;

public class ApiUtils {

	public static <T> ResponseEntity<ApiResult<T>> success(T response) {
		return new ResponseEntity<>(new ApiResult<>(true, response, null), HttpStatus.OK);
	}

	public static ResponseEntity<ApiResult<?>> error(ErrorCode errorCode) {
		return new ResponseEntity<>(new ApiResult<>(false, null, errorCode), errorCode.getStatus());
	}

	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final ErrorCode error;

		private ApiResult(boolean success, T response, ErrorCode errorCode) {
			this.success = success;
			this.response = response;
			this.error = errorCode;
		}

		public boolean isSuccess() {
			return success;
		}

		public ErrorCode getError() {
			return error;
		}

		public T getResponse() {
			return response;
		}
	}
}