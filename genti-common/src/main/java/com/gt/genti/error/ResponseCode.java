package com.gt.genti.error;

import static com.gt.genti.constants.ErrorConstants.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gt.genti.constants.ErrorConstants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {
	OK(null, HttpStatus.OK, true, null),
	SEE_OTHER(null, HttpStatus.SEE_OTHER, true, null),
	/**
	 * 인증/인가
	 */
	TOKEN_NOT_PROVIDED(ErrorConstants.TOKEN_NOT_PROVIDED, HttpStatus.UNAUTHORIZED, false, "토큰이 전달되지 않았습니다."),
	TOKEN_EXPIRED(ErrorConstants.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED, false, "토큰이 만료되었습니다."),
	INVALID_TOKEN(ErrorConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED, false, "유효하지 않은 토큰입니다."),
	INSUFFICIENT_PERMISSIONS(ErrorConstants.INSUFFICIENT_PERMISSIONS, HttpStatus.FORBIDDEN, false, "접근 권한이 부족합니다."),
	REFRESH_TOKEN_EXPIRED(ErrorConstants.REFRESH_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED, false, "리프레시 토큰이 만료되었습니다."),
	REFRESH_TOKEN_INVALID(ErrorConstants.REFRESH_TOKEN_INVALID, HttpStatus.UNAUTHORIZED, false, "유효하지 않은 리프레시 토큰입니다."),
	TOKEN_REFRESH_FAILED(ErrorConstants.TOKEN_REFRESH_FAILED, HttpStatus.UNAUTHORIZED, false,
		"토큰 갱신에 실패했습니다."),
	INVALID_REFRESH_TOKEN(ErrorConstants.INVALID_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED, false,
		"토큰 갱신에 실패했습니다."),
	Forbidden(ErrorConstants.Forbidden, HttpStatus.FORBIDDEN, false,
		"토큰 갱신에 실패했습니다."),
	UnAuthorized(ErrorConstants.UnAuthorized, HttpStatus.UNAUTHORIZED, false,
		"인증되지 않은 사용자."),
	EncryptAlgorithmDeprecated(ErrorConstants.EncryptAlgorithmDeprecated, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"%s 암호화 과정 중 문제가 발생했습니다."),

	/**
	 * Oauth
	 */
	OauthProviderNotAllowed(ErrorConstants.OauthProviderNotAllowed, HttpStatus.NOT_ACCEPTABLE, false,
		"허가되지 않은 oauth type %s"),
	AppleOauthIdTokenIncorrect(ErrorConstants.AppleOauthIdTokenIncorrect, HttpStatus.BAD_REQUEST, false,
		"Apple OAuth Identity Token 형식이 올바르지 않습니다."),
	AppleOauthIdTokenExpired(ErrorConstants.AppleOauthIdTokenExpired, HttpStatus.REQUEST_TIMEOUT, false,
		"Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다."),
	AppleOauthIdTokenInvalid(ErrorConstants.AppleOauthIdTokenInvalid, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"Apple OAuth Identity Token 값이 올바르지 않습니다."),
	AppleOauthClaimInvalid(ErrorConstants.AppleOauthClaimInvalid, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"Apple OAuth Claims 값이 올바르지 않습니다."),
	AppleOauthPublicKeyInvalid(ErrorConstants.AppleOauthPublicKeyInvalid, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다."),
	AppleOauthJwtValueInvalid(ErrorConstants.AppleOauthJwtValueInvalid, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"Apple JWT 값의 alg, kid 정보가 올바르지 않습니다."),
	/**
	 * 서버 오류 및 올바르지 않은 요청
	 */
	NotNullableEnum(ErrorConstants.NotNullableEnum, HttpStatus.BAD_REQUEST, false, " [%s] 값은 null 값을 허용하지 않습니다."),
	DBToEnumFailed(ErrorConstants.DBToEnumFailed, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"문자열 -> Enum 변환 실패 enum : %s 입력된 값 : %s"),
	HandlerMethodValidation(ErrorConstants.HandlerMethodValidation, HttpStatus.BAD_REQUEST, false, "%s"),
	UnrecognizedProperty(ErrorConstants.UnrecognizedProperty, HttpStatus.BAD_REQUEST, false,
		"json property parsing 중 오류 발생, %s"),
	InvalidDataAccessApiUsage(ErrorConstants.InvalidDataAccessApiUsage, HttpStatus.BAD_REQUEST, false,
		"%s"),
	HandlerNotFound(ErrorConstants.HandlerNotFound, HttpStatus.NOT_FOUND, false,
		"요청 uri에 대한 핸들러가 없습니다."),
	HttpRequestMethodNotSupported(ErrorConstants.HttpRequestMethodNotSupported, HttpStatus.METHOD_NOT_ALLOWED, false,
		"not allowed methods, allowed methods : %s"),
	MethodArgumentTypeMismatch(ErrorConstants.MethodArgumentTypeMismatch, HttpStatus.BAD_REQUEST, false, "%s"),
	MissingPathVariable(ErrorConstants.MissingPathVariableException, HttpStatus.BAD_REQUEST, false,
		"query param 에러 %s "),
	UnHandledException(ErrorConstants.UnHandledException, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"예기치 못한 문제가 발생했습니다. 오류내용 : %s"),
	TimeOut(ErrorConstants.UnHandledException, HttpStatus.REQUEST_TIMEOUT, false, "요청 시간이 초과되었습니다."),

	/**
	 * Discord
	 */
	NoWebhookEmbeds(ErrorConstants.NoWebhookEmbeds, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"디스코드 Webhook embed 정보가 없습니다."),
	DiscordIOException(ErrorConstants.DiscordIOException, HttpStatus.INTERNAL_SERVER_ERROR, false,
		"디스코드 IO 오류 발생 상세 : %s"),

	/**
	 * PictureGenerateRequest
	 */
	ActivePictureGenerateRequestNotExists(ErrorConstants.ActivePictureGenerateRequestNotExists, HttpStatus.NOT_FOUND,
		false,
		"현재 진행중인 요청이 없습니다."),
	PictureGenerateRequestNotFound(ErrorConstants.PictureGenerateRequestNotFound, HttpStatus.NOT_FOUND, false,
		"사진 생성 요청을 찾지 못했습니다. 조회 조건 : %s"),
	UnexpectedPictureGenerateRequestStatus(ErrorConstants.UnexpectedPictureGenerateRequestStatus, HttpStatus.CONFLICT,
		false, "현재 사진생성요청의 상태가 [%s]임, 요청을 수행할 수 없습니다."),
	NoPictureGenerateRequest(ErrorConstants.NoPictureGenerateRequest, HttpStatus.NO_CONTENT, false,
		"사진 생성요청이 없습니다."),
	PictureGenerateRequestAlreadyInProgress(ErrorConstants.PictureGenerateRequestAlreadyInProgress,
		HttpStatus.BAD_REQUEST, false, "이미 작업이 진행중인 요청은 수정이 불가합니다."),
	PictureGenerateRequestNotAssignedToCreator(ErrorConstants.PictureGenerateRequestNotAssignedToCreator,
		HttpStatus.BAD_REQUEST,
		false,
		"현재 공급자에게 매칭된 요청이 아닙니다."),
	PictureGenerateRequestNotAcceptableDueToExpired(ErrorConstants.PictureGenerateRequestNotAcceptableDueToExpired,
		HttpStatus.BAD_REQUEST,
		false, "수락 마감 시간을 초과하였습니다."),
	OnlyRequesterCanViewPictureGenerateRequest(ErrorConstants.OnlyRequesterCanViewPictureGenerateRequest,
		HttpStatus.FORBIDDEN,
		false,
		"사진생성요청을 요청한 유저만 볼 수 있습니다."),
	/**
	 * User
	 */
	AlreadyActivatedUser(ErrorConstants.AlreadyActivatedUser, HttpStatus.BAD_REQUEST, false, "비활성화 되지 않은 상태의 유저입니다."),
	CannotRestoreUser(ErrorConstants.CannotRestoreUser, HttpStatus.BAD_REQUEST, false,
		"탈퇴 후 한달이 지난 경우 재가입해야합니다. 찾은 userId : [%s]"),
	UserNotLoggedIn(ErrorConstants.UserNotLoggedIn, HttpStatus.UNAUTHORIZED, false, "로그아웃되었습니다. 다시 로그인해주세요"),
	WithDrawnUser(ErrorConstants.WithDrawnUser, HttpStatus.BAD_REQUEST, false, "탈퇴한 사용자입니다."),
	UserNotFound(ErrorConstants.UserNotFound, HttpStatus.NOT_FOUND, false, "존재하지 않는 사용자입니다. 찾은 userId: [%s]"),
	UserDeactivated(ErrorConstants.UserDeactivated, HttpStatus.BAD_REQUEST, false, "비활성화된 계정입니다."),

	/**
	 * Creator
	 */
	CreatorNotFound(ErrorConstants.CreatorNotFound, HttpStatus.NOT_FOUND, false, "userId [%d] 에 해당하는 공급자를 찾을 수 없습니다."),

	/**
	 * Picture
	 */
	PictureNotFound(ErrorConstants.PictureNotFound, HttpStatus.NOT_FOUND, false, "사진을 찾을 수 없습니다."),
	PictureUserFaceNotFound(ErrorConstants.PictureUserFaceNotFound, HttpStatus.NOT_FOUND, false,
		"유저의 얼굴 사진을 찾지 못했습니다."),
	PicturePoseNotFound(ErrorConstants.PicturePoseNotFound, HttpStatus.NOT_FOUND, false, "포즈 참고 사진을 찾지 못했습니다."),
	PictureCompletedNotFound(ErrorConstants.PictureCompletedNotFound, HttpStatus.NOT_FOUND, false,
		"최종 완성 사진을 찾지 못했습니다."),
	PictureCreatedByCreatorNotFound(ErrorConstants.PictureCreatedByCreatorNotFound, HttpStatus.NOT_FOUND, false,
		"공급자가 제출한 1차 완성 사진을 찾지 못했습니다."),
	PictureProfileNotFound(ErrorConstants.PictureProfileNotFound, HttpStatus.NOT_FOUND, false,
		"해당하는 유저 프로필 사진을 찾지 못했습니다."),
	UploadFileTypeNotAvailable(ErrorConstants.UploadFileTypeNotAvailable, HttpStatus.NOT_ACCEPTABLE, false,
		"업로드 가능한 파일이 아닙니다."),

	/**
	 * PictureGenerateResponse
	 */
	PictureGenerateResponseNotFound(ErrorConstants.PictureGenerateResponseNotFound, HttpStatus.NOT_FOUND, false,
		"해당하는 사진생성응답을 찾을 수 없습니다."),
	FinalPictureNotUploadedYet(ErrorConstants.FinalPictureNotUploadedYet, HttpStatus.BAD_REQUEST, false,
		"최종 작업 사진이 제출되지 않았습니다. 사진생성응답 id : [%d]"),
	CreatorsPictureNotUploadedYet(ErrorConstants.CreatorsPictureNotUploadedYet, HttpStatus.BAD_REQUEST, false,
		"공급자 작업 사진이 제출되지 않았습니다."),
	RequestBlockedDueToPictureGenerateResponseStatus(PGRESStateException, HttpStatus.NOT_ACCEPTABLE,
		false,
		"현재 사진생성응답 상태가 [%s] 이므로 해당 요청을 수행할 수 없습니다."),
	SubmitBlockedDueToPictureGenerateResponseIsExpired(
		ErrorConstants.SubmitBlockedDueToPictureGenerateResponseIsExpired, HttpStatus.BAD_REQUEST,
		false,
		"제출 마감 시간을 초과하였습니다."),
	AlreadyCompletedPictureGenerateResponse(ErrorConstants.AlreadyCompletedPictureGenerateResponse,
		HttpStatus.BAD_REQUEST, false,
		"이미 처리 완료된 응답입니다."),

	/**
	 * Report
	 */
	ReportNotFound(ErrorConstants.ReportNotFound, HttpStatus.NOT_FOUND, false, "해당 report 건을 찾지 못했습니다."),
	/**
	 * Deposit
	 */
	DepositNotFound(ErrorConstants.DepositNotFound, HttpStatus.NOT_FOUND, false, "사용자의 포인트 정보를 불러올 수 없습니다."),
	AddPointAmountCannotBeMinus(ErrorConstants.AddPointAmountCannotBeMinus, HttpStatus.BAD_REQUEST, false,
		"적립될 포인트는 음수일 수 없습니다."),
	NotEnoughBalance(ErrorConstants.NotEnoughBalance, HttpStatus.NOT_ACCEPTABLE, false,
		"해당 공급자의 출금가능 잔액이 부족하여 요청을 완료할 수 없습니다."),
	/**
	 * WithdrawRequest && Settlement
	 */
	CannotCreateWithdrawalDueToSettlementsNotAvailable(
		ErrorConstants.CannotCreateWithdrawalDueToSettlementsNotAvailable, HttpStatus.BAD_REQUEST, false,
		"출금 가능한 정산 내역이 없습니다."),
	WithdrawRequestNotFound(ErrorConstants.WithdrawRequestNotFound, HttpStatus.NOT_FOUND, false,
		"해당 출금 요청을 찾을 수 없습니다."),
	HttpMessageNotReadable(ErrorConstants.HttpMessageNotReadable, HttpStatus.BAD_REQUEST, false, "잘못된 입력 : %s"),
	FileTypeNotProvided(ErrorConstants.FileTypeNotProvided, HttpStatus.BAD_REQUEST, false, "파일 형식이 주어지지 않았습니다.");

	private final String errorCode;
	private final HttpStatusCode httpStatusCode;
	private final boolean success;
	private final String errorMessage;

	public String getErrorMessage(Object... args) {
		if (this.success) {
			return null;
		}
		int placeholderCount = countPlaceholders(errorMessage);
		if (args.length != placeholderCount) {
			return errorMessage.replaceAll("%[sd]", "[동적으로 생성될 부분]");
		}
		return String.format(errorMessage, args);
	}

	private int countPlaceholders(String message) {
		Pattern pattern = Pattern.compile("%[sd]");
		Matcher matcher = pattern.matcher(message);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}
}

