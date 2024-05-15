package com.gt.genti.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gt.genti.other.util.ErrorUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
	TOKEN_NOT_PROVIDED(HttpStatus.UNAUTHORIZED, ErrorUtils.TOKEN_NOT_PROVIDED, "토큰이 전달되지 않았습니다."),
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, ErrorUtils.TOKEN_EXPIRED, "토큰이 만료되었습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, ErrorUtils.INVALID_TOKEN, "유효하지 않은 토큰입니다."),
	INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, ErrorUtils.INSUFFICIENT_PERMISSIONS, "접근 권한이 부족합니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, ErrorUtils.REFRESH_TOKEN_EXPIRED, "리프레시 토큰이 만료되었습니다."),
	REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, ErrorUtils.REFRESH_TOKEN_INVALID, "유효하지 않은 리프레시 토큰입니다."),
	TOKEN_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.TOKEN_CREATION_FAILED, "토큰 생성에 실패했습니다."),
	TOKEN_REFRESH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.TOKEN_REFRESH_FAILED, "토큰 갱신에 실패했습니다."),
	UnHandledException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.UnHandledException, "예기치 못한 문제가 발생했습니다."),
	ActivePictureGenerateRequestNotExists(HttpStatus.NO_CONTENT, ErrorUtils.ActivePictureGenerateRequestNotExists,
		"현재 진행중인 요청이 없습니다."),
	FileTypeNotProvided(HttpStatus.NO_CONTENT, ErrorUtils.FileTypeNotProvided, "파일 형식이 주어지지 않았습니다."),
	PictureGenerateRequestNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureGenerateRequestNotFound,
		"사진 생성 요청을 찾지 못했습니다."),
	UserNotFound(HttpStatus.NOT_FOUND, ErrorUtils.UserNotFound, "사용자를 찾지 못했습니다."),
	RequestAlreadyInProgress(HttpStatus.BAD_REQUEST, ErrorUtils.RequestAlreadyInProgress, "이미 작업이 진행중인 요청은 수정이 불가합니다."),
	ZeroMatchingRequests(HttpStatus.NO_CONTENT, ErrorUtils.ZeroMatchingRequests, "매칭된 요청이 없습니다."),
	UploadFileTypeNotAvailable(HttpStatus.NO_CONTENT, ErrorUtils.UploadFileTypeNotAvailable, "업로드 가능한 파일이 아닙니다."),
	PictureGenerateResponseNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureGenerateResponseNotFound,
		"해당하는 사진생성요청 응답을 찾을 수 없습니다."),
	ReportNotFound(HttpStatus.NOT_FOUND, ErrorUtils.ReportNotFound, "해당 report 건을 찾지 못했습니다."),
	NotSupportedTemp(HttpStatus.BAD_REQUEST, ErrorUtils.NotSupportedTemp, "지원되지않는 조회조건입니다."),
	CreatorNotFound(HttpStatus.NOT_FOUND, ErrorUtils.CreatorNotFound, "공급자를 찾을 수 없습니다."),
	PictureNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureNotFound, "사진을 찾을 수 없습니다.");

	private final HttpStatusCode status;
	private final String code;
	private final String message;
}