package com.gt.genti.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
	// AuthorizationHeaderNotGiven(HttpStatus.OK, "A001", "")
	UnHandledException(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "예기치 못한 문제가 발생했습니다."),
	ActivePictureGenerateRequestNotExists(HttpStatus.NO_CONTENT, "A001", "현재 진행중인 요청이 없습니다."),
	PictureGenerateRequestNotFound(HttpStatus.NOT_FOUND, "A002", "사진 생성 요청을 찾지 못했습니다."),
	UserNotFound(HttpStatus.NOT_FOUND, "U001", "사용자를 찾지 못했습니다."),
	EmailAlreadyExists(HttpStatus.CONFLICT, "A001", "사용중인 이메일입니다."),
	LoginIdAlreadyExists(HttpStatus.CONFLICT, "A002", "사용중인 아이디입니다.");


	private final HttpStatusCode status;
	private final String code;
	private final String message;
}