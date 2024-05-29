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
	ActivePictureGenerateRequestNotExists(HttpStatus.NOT_FOUND, ErrorUtils.ActivePictureGenerateRequestNotExists,
		"현재 진행중인 요청이 없습니다."),
	FileTypeNotProvided(HttpStatus.BAD_REQUEST, ErrorUtils.FileTypeNotProvided, "파일 형식이 주어지지 않았습니다."),
	PictureGenerateRequestNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureGenerateRequestNotFound,
		"사진 생성 요청을 찾지 못했습니다."),
	UserNotFound(HttpStatus.NOT_FOUND, ErrorUtils.UserNotFound, "사용자를 찾지 못했습니다."),
	RequestAlreadyInProgress(HttpStatus.BAD_REQUEST, ErrorUtils.RequestAlreadyInProgress, "이미 작업이 진행중인 요청은 수정이 불가합니다."),
	ZeroMatchingRequests(HttpStatus.NO_CONTENT, ErrorUtils.ZeroMatchingRequests, "매칭된 요청이 없습니다."),
	UploadFileTypeNotAvailable(HttpStatus.NOT_ACCEPTABLE, ErrorUtils.UploadFileTypeNotAvailable, "업로드 가능한 파일이 아닙니다."),
	PictureGenerateResponseNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureGenerateResponseNotFound,
		"해당하는 사진생성요청 응답을 찾을 수 없습니다."),
	ReportNotFound(HttpStatus.NOT_FOUND, ErrorUtils.ReportNotFound, "해당 report 건을 찾지 못했습니다."),
	NotSupportedTemp(HttpStatus.BAD_REQUEST, ErrorUtils.NotSupportedTemp, "지원되지않는 조회조건입니다."),
	CreatorNotFound(HttpStatus.NOT_FOUND, ErrorUtils.CreatorNotFound, "공급자를 찾을 수 없습니다."),
	PictureNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureNotFound, "사진을 찾을 수 없습니다."),
	NotAssignedToMe(HttpStatus.BAD_REQUEST, ErrorUtils.NotAssignedToMe, "나에게 매칭된 요청이 아닙니다."),
	NotMatchedYet(HttpStatus.BAD_REQUEST, ErrorUtils.NotMatchedYet, "아직 매칭되지 않은 요청을 조회할 수 없습니다."),
	ExpiredPictureGenerateRequest(HttpStatus.BAD_REQUEST, ErrorUtils.ExpiredPictureGenerateRequest,
		"제출 마감 시간을 초과하였습니다."),
	ExpiredMatching(HttpStatus.BAD_REQUEST, ErrorUtils.ExpiredMatching, "수락 마감 시간을 초과하였습니다."),
	DepositNotFound(HttpStatus.NOT_FOUND, ErrorUtils.DepositNotFound, "사용자의 포인트 정보를 불러올 수 없습니다."),
	AddPointAmountCannotBeMinus(HttpStatus.BAD_REQUEST, ErrorUtils.AddPointAmountCannotBeMinus, "적립될 포인트는 음수일 수 없습니다."),
	FinalPictureNotUploadedYet(HttpStatus.BAD_REQUEST, ErrorUtils.FinalPictureNotUploadedYet, "최종 작업 사진이 제출되지 않았습니다."),
	CreatorsPictureNotUploadedYet(HttpStatus.BAD_REQUEST, ErrorUtils.CreatorsPictureNotUploadedYet,
		"작업 사진이 제출되지 않았습니다."),
	UserDeactivated(HttpStatus.BAD_REQUEST, ErrorUtils.UserDeactivated, "비활성화된 계정입니다."),
	AlreadyActivatedUser(HttpStatus.BAD_REQUEST, ErrorUtils.AlreadyActivatedUser, "비활성화 되지 않은 상태의 유저입니다."),
	CannotRestoreUser(HttpStatus.BAD_REQUEST, ErrorUtils.CannotRestoreUser, "탈퇴 후 한달이 지난 경우 재가입해야합니다."),
	PictureUserFaceNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureUserFaceNotFound, "유저의 얼굴 사진을 찾지 못했습니다."),
	PicturePoseNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PicturePoseNotFound, "포즈 참고 사진을 찾지 못했습니다."),
	PictureCompletedNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureCompletedNotFound, "최종 완성 사진을 찾지 못했습니다."),
	PictureCreatedByCreatorNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureCreatedByCreatorNotFound,
		"공급자가 제출한 1차 완성 사진을 찾지 못했습니다."),
	PictureProfileNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureProfileNotFound,
		"해당하는 유저 프로필 사진을 찾지 못했습니다."),
	NotNullableEnum(HttpStatus.BAD_REQUEST, ErrorUtils.NotNullableEnum, " [%s] 값은 null 값을 허용하지 않습니다."),
	DBToEnumFailed(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.DBToEnumFailed,
		"DB -> ENUM 값 불러오기 실패  enum : %s value :  %s detail : %s"),
	WithDrawnUser(HttpStatus.BAD_REQUEST, ErrorUtils.WithDrawnUser, "탈퇴한 사용자입니다."),
	Undefined(HttpStatus.INTERNAL_SERVER_ERROR, ErrorUtils.Undefined, "FOR FE 원래 비즈니스 로직 상 발생하면 안되는 오류입니다. 문의 부탁드립니다."),
	ValidationError(HttpStatus.BAD_REQUEST, ErrorUtils.ControllerValidationError,"%s"),
	NotAllowedOauthProvider(HttpStatus.NOT_ACCEPTABLE,ErrorUtils.NotAllowedOauthProvider , "허가되지 않은 oauth type %s");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	public String getMessage(Object... args) {
		return String.format(message, args);
	}

}