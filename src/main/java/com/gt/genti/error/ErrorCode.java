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
	FileTypeNotProvided(HttpStatus.NO_CONTENT, "G001", "파일 형식이 주어지지 않았습니다."),
	PictureGenerateRequestNotFound(HttpStatus.NOT_FOUND, "A002", "사진 생성 요청을 찾지 못했습니다."),
	UserNotFound(HttpStatus.NOT_FOUND, "U001", "사용자를 찾지 못했습니다."),
	RequestAlreadyInProgress(HttpStatus.BAD_REQUEST, "U002", "이미 작업이 진행중인 요청은 수정이 불가합니다."),
	ZeroMatchingRequests(HttpStatus.NO_CONTENT, "G001", "매칭된 요청이 없습니다."),
	UploadFileTypeNotAvailable(HttpStatus.NO_CONTENT, "G002", "업로드 가능한 파일이 아닙니다."),
	PictureGenerateResponseNotFound(HttpStatus.NOT_FOUND, "G003", "해당하는 사진생성요청 응답을 찾을 수 없습니다."),
	LoginIdAlreadyExists(HttpStatus.CONFLICT, "A002", "사용중인 아이디입니다.");

	private final HttpStatusCode status;
	private final String code;
	private final String message;
}