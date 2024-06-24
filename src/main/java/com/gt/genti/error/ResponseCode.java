package com.gt.genti.error;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gt.genti.other.util.ErrorConstants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
	OK(true, HttpStatus.OK, ResponseConstants.OK, "요청완료"),

	/**
	 * 인증/인가
	 */
	TOKEN_NOT_PROVIDED(false, HttpStatus.UNAUTHORIZED, ErrorConstants.TOKEN_NOT_PROVIDED, "토큰이 전달되지 않았습니다."),
	TOKEN_EXPIRED(false, HttpStatus.UNAUTHORIZED, ErrorConstants.TOKEN_EXPIRED, "토큰이 만료되었습니다."),
	INVALID_TOKEN(false, HttpStatus.UNAUTHORIZED, ErrorConstants.INVALID_TOKEN, "유효하지 않은 토큰입니다."),
	INSUFFICIENT_PERMISSIONS(false, HttpStatus.FORBIDDEN, ErrorConstants.INSUFFICIENT_PERMISSIONS, "접근 권한이 부족합니다."),
	REFRESH_TOKEN_EXPIRED(false, HttpStatus.UNAUTHORIZED, ErrorConstants.REFRESH_TOKEN_EXPIRED, "리프레시 토큰이 만료되었습니다."),
	REFRESH_TOKEN_INVALID(false, HttpStatus.UNAUTHORIZED, ErrorConstants.REFRESH_TOKEN_INVALID, "유효하지 않은 리프레시 토큰입니다."),
	TOKEN_REFRESH_FAILED(false, HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.TOKEN_REFRESH_FAILED,
		"토큰 갱신에 실패했습니다."),

	/**
	 * Oauth
	 */
	OauthProviderNotAllowed(false, HttpStatus.NOT_ACCEPTABLE, ErrorConstants.NotAllowedOauthProvider,
		"허가되지 않은 oauth type %s"),
	/**
	 * 서버 오류 및 올바르지 않은 요청
	 */
	NotNullableEnum(false, HttpStatus.BAD_REQUEST, ErrorConstants.NotNullableEnum, " [%s] 값은 null 값을 허용하지 않습니다."),
	DBToEnumFailed(false, HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.DBToEnumFailed,
		"문자열 -> Enum 변환 실패 enum : %s 입력된 값 : %s"),
	ControllerValidationError(false, HttpStatus.BAD_REQUEST, ErrorConstants.ControllerValidationError, "%s"),
	UnrecognizedPropertyException(false, HttpStatus.BAD_REQUEST, ErrorConstants.UnrecognizedPropertyException,
		"json property parsing 중 오류 발생, %s"),
	InvalidDataAccessApiUsageException(false, HttpStatus.BAD_REQUEST, ErrorConstants.InvalidDataAccessApiUsageException,
		"%s"),
	NoHandlerFoundException(false, HttpStatus.NOT_FOUND, ErrorConstants.NoHandlerFoundException,
		"요청 uri에 대한 핸들러가 없습니다."),
	MethodNotSupported(false, HttpStatus.METHOD_NOT_ALLOWED, ErrorConstants.MethodNotSupported,
		"not allowed methods, allowed methods : %s"),
	MethodArgumentTypeMismatch(false, HttpStatus.BAD_REQUEST, ErrorConstants.MethodArgumentTypeMismatch, "%s"),
	MissingPathVariableException(false, HttpStatus.BAD_REQUEST, ErrorConstants.MissingPathVariableException,
		"query param 에러 %s "),
	UnHandledException(false, HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.UnHandledException,
		"예기치 못한 문제가 발생했습니다. 오류내용 : %s"),

	/**
	 * PictureGenerateRequest
	 */
	ActivePictureGenerateRequestNotExists(false, HttpStatus.NOT_FOUND,
		ErrorConstants.ActivePictureGenerateRequestNotExists,
		"현재 진행중인 요청이 없습니다."),
	PictureGenerateRequestNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureGenerateRequestNotFound,
		"사진 생성 요청을 찾지 못했습니다. 조회 조건 : %s"),
	UnexpectedPictureGenerateRequestStatus(false, HttpStatus.CONFLICT,
		ErrorConstants.UnexpectedPictureGenerateRequestStatus, "현재 사진생성요청의 상태가 [%s]임, 요청을 수행할 수 없습니다."),
	NoPictureGenerateRequest(false, HttpStatus.NO_CONTENT, ErrorConstants.NoPictureGenerateRequest,
		"사진 생성요청이 1개도 없습니다."),
	PictureGenerateRequestAlreadyInProgress(false, HttpStatus.BAD_REQUEST,
		ErrorConstants.PictureGenerateRequestAlreadyInProgress,
		"이미 작업이 진행중인 요청은 수정이 불가합니다."),
	PictureGenerateRequestNotAssignedToCreator(false, HttpStatus.BAD_REQUEST,
		ErrorConstants.PictureGenerateRequestNotAssignedToCreator,
		"현재 공급자에게 매칭된 요청이 아닙니다."),
	PictureGenerateRequestNotAcceptableDueToExpired(false, HttpStatus.BAD_REQUEST,
		ErrorConstants.PictureGenerateRequestNotAcceptableDueToExpired, "수락 마감 시간을 초과하였습니다."),
	PictureGenerateRequestVisibilityRestrictedToRequester(false, HttpStatus.FORBIDDEN,
		ErrorConstants.OnlyRequesterCanViewRequest,
		"사진생성요청을 요청한 유저만 볼 수 있습니다."),
	/**
	 * User
	 */
	AlreadyActivatedUser(false, HttpStatus.BAD_REQUEST, ErrorConstants.AlreadyActivatedUser, "비활성화 되지 않은 상태의 유저입니다."),
	CannotRestoreUser(false, HttpStatus.BAD_REQUEST, ErrorConstants.CannotRestoreUser,
		"탈퇴 후 한달이 지난 경우 재가입해야합니다. 찾은 userId : [%s]"),
	UserNotLoggedIn(false, HttpStatus.UNAUTHORIZED, ErrorConstants.UserNotLoggedIn, "로그아웃되었습니다. 다시 로그인해주세요"),
	WithDrawnUser(false, HttpStatus.BAD_REQUEST, ErrorConstants.WithDrawnUser, "탈퇴한 사용자입니다."),
	UserNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.UserNotFound, "존재하지 않는 사용자입니다. 찾은 userId: [%s]"),
	UserDeactivated(false, HttpStatus.BAD_REQUEST, ErrorConstants.UserDeactivated, "비활성화된 계정입니다."),

	/**
	 * Creator
	 */
	CreatorNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.CreatorNotFound, "userId [%s] 에 해당하는 공급자를 찾을 수 없습니다."),

	/**
	 * Picture
	 */
	PictureNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureNotFound, "사진을 찾을 수 없습니다."),
	PictureUserFaceNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureUserFaceNotFound,
		"유저의 얼굴 사진을 찾지 못했습니다."),
	PicturePoseNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PicturePoseNotFound, "포즈 참고 사진을 찾지 못했습니다."),
	PictureCompletedNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureCompletedNotFound,
		"최종 완성 사진을 찾지 못했습니다."),
	PictureCreatedByCreatorNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureCreatedByCreatorNotFound,
		"공급자가 제출한 1차 완성 사진을 찾지 못했습니다."),
	PictureProfileNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureProfileNotFound,
		"해당하는 유저 프로필 사진을 찾지 못했습니다."),
	UploadFileTypeNotAvailable(false, HttpStatus.NOT_ACCEPTABLE, ErrorConstants.UploadFileTypeNotAvailable,
		"업로드 가능한 파일이 아닙니다."),

	/**
	 * PictureGenerateResponse
	 */
	PictureGenerateResponseNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.PictureGenerateResponseNotFound,
		"해당하는 사진생성응답을 찾을 수 없습니다."),
	FinalPictureNotUploadedYet(false, HttpStatus.BAD_REQUEST, ErrorConstants.FinalPictureNotUploadedYet,
		"최종 작업 사진이 제출되지 않았습니다."),
	CreatorsPictureNotUploadedYet(false, HttpStatus.BAD_REQUEST, ErrorConstants.CreatorsPictureNotUploadedYet,
		"공급자 작업 사진이 제출되지 않았습니다."),
	RequestBlockedDueToPictureGenerateResponseStatus(false, HttpStatus.NOT_ACCEPTABLE,
		ErrorConstants.PGRESStateException,
		"현재 사진생성응답 상태가 [%s] 이므로 해당 요청을 수행할 수 없습니다."),
	SubmitBlockedDueToPictureGenerateResponseIsExpired(false, HttpStatus.BAD_REQUEST,
		ErrorConstants.SubmitBlockedDueToPictureGenerateResponseIsExpired,
		"제출 마감 시간을 초과하였습니다."),
	AlreadyCompletedPictureGenerateResponse(false, HttpStatus.BAD_REQUEST, ErrorConstants.AlreadyCompletedResponse,
		"이미 처리 완료된 응답입니다."),
	/**
	 * Report
	 */
	ReportNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.ReportNotFound, "해당 report 건을 찾지 못했습니다."),

	/**
	 * Deposit
	 */
	DepositNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.DepositNotFound, "사용자의 포인트 정보를 불러올 수 없습니다."),
	AddPointAmountCannotBeMinus(false, HttpStatus.BAD_REQUEST, ErrorConstants.AddPointAmountCannotBeMinus,
		"적립될 포인트는 음수일 수 없습니다."),
	NotEnoughBalance(false, HttpStatus.NOT_ACCEPTABLE, ErrorConstants.NotEnoughBalance,
		"해당 공급자의 출금가능 잔액이 부족하여 요청을 완료할 수 없습니다."),

	/**
	 * WithdrawRequest && Settlement
	 */
	NoSettlementForWithdrawalException(false, HttpStatus.BAD_REQUEST, ErrorConstants.NoSettlementForWithdrawalException,
		"출금 가능한 정산 내역이 없습니다."),
	WithdrawRequestNotFound(false, HttpStatus.NOT_FOUND, ErrorConstants.WithdrawRequestNotFound,
		"해당 출금 요청을 찾을 수 없습니다."),

	InValidFormat(false, HttpStatus.BAD_REQUEST, ErrorConstants.InValidFormat, "잘못된 입력 : %s"),
	FileTypeNotProvided(false, HttpStatus.BAD_REQUEST, ErrorConstants.FileTypeNotProvided, "파일 형식이 주어지지 않았습니다.");

	private final boolean success;
	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	public String getMessage(Object... args) {
		int placeholderCount = countPlaceholders(message);
		if (args.length != placeholderCount) {
			// Handle the case where the number of placeholders does not match the number of arguments
			return message.replaceAll("%s", "example");
		}
		return String.format(message, args);
	}

	private int countPlaceholders(String message) {
		Pattern pattern = Pattern.compile("%s");
		Matcher matcher = pattern.matcher(message);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}
}

