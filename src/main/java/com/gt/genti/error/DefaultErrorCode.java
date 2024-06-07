package com.gt.genti.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gt.genti.other.util.ErrorUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DefaultErrorCode implements ErrorCode {
	TOKEN_NOT_PROVIDED(HttpStatus.UNAUTHORIZED, ErrorUtils.TOKEN_NOT_PROVIDED, "토큰이 전달되지 않았습니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, ErrorUtils.TOKEN_EXPIRED, "토큰이 만료되었습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, ErrorUtils.INVALID_TOKEN, "유효하지 않은 토큰입니다."),
	INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, ErrorUtils.INSUFFICIENT_PERMISSIONS, "접근 권한이 부족합니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, ErrorUtils.REFRESH_TOKEN_EXPIRED, "리프레시 토큰이 만료되었습니다."),
	REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, ErrorUtils.REFRESH_TOKEN_INVALID, "유효하지 않은 리프레시 토큰입니다."),
	TOKEN_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.TOKEN_CREATION_FAILED, "토큰 생성에 실패했습니다."),
	TOKEN_REFRESH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.TOKEN_REFRESH_FAILED, "토큰 갱신에 실패했습니다."),
	UnHandledException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.UnHandledException,
		"예기치 못한 문제가 발생했습니다.\n오류내용 : \n%s"),
	NotNullableEnum(HttpStatus.BAD_REQUEST, ErrorUtils.NotNullableEnum, " [%s] 값은 null 값을 허용하지 않습니다."),
	DBToEnumFailed(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.DBToEnumFailed,
		"DB -> ENUM 값 불러오기 실패  enum : %s value :  %s detail : %s"),
	ValidationError(HttpStatus.BAD_REQUEST, ErrorUtils.ControllerValidationError, "%s"),
	UnrecognizedPropertyException(HttpStatus.BAD_REQUEST, ErrorUtils.UnrecognizedPropertyException, "%s"),
	InvalidDataAccessApiUsageException(HttpStatus.BAD_REQUEST, ErrorUtils.InvalidDataAccessApiUsageException, "%s"),
	NoHandlerFoundException(HttpStatus.NOT_FOUND, ErrorUtils.NoHandlerFoundException, "요청 uri : [%s] 핸들러가 없습니다.");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	@Override
	public String getMessage(Object... args) {
		return String.format(message, args);
	}

}

