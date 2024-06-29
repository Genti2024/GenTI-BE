package com.gt.genti.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gt.genti.error.ResponseCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GentiResponse {

	public static <T> ResponseEntity<ApiResult<T>> success(T response, ResponseCode responseCode) {
		return new ResponseEntity<>(new ApiResult<>(true, response, responseCode), HttpStatus.OK);
	}

	public static <T> ResponseEntity<ApiResult<T>> success(T response) {
		return success(response, ResponseCode.OK);
	}

	public static ResponseEntity<ApiResult<?>> error(ResponseCode code, Object... args) {
		return new ResponseEntity<>(new ApiResult<>(false, null, code, args), code.getHttpStatusCode());
	}

	@Getter
	@Schema
	public static class ApiResult<T> {
		@Schema(name = "success", description = "api요청 성공 여부")
		private final boolean success;
		@Schema(name = "response", description = "응답 데이터, 실패시 null")
		private final T response;
		@Schema(name = "code", description = "코드 ")
		private final String code;
		@Schema(name = "message", description = "message")
		private final String message;
		@Schema(name = "status", description = "http status")
		private final int status;

		public ApiResult(boolean success, T response, ResponseCode responseCode, Object... args) {
			this.success = success;
			this.response = response;
			this.code = responseCode.getCode();
			this.message = responseCode.getMessage(args);
			this.status = responseCode.getHttpStatusCode().value();
		}
	}
}