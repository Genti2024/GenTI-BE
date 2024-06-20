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
public enum DomainErrorCode implements ErrorCode {
	ActivePictureGenerateRequestNotExists(HttpStatus.NOT_FOUND, ErrorUtils.ActivePictureGenerateRequestNotExists,
		"현재 진행중인 요청이 없습니다."),
	FileTypeNotProvided(HttpStatus.BAD_REQUEST, ErrorUtils.FileTypeNotProvided, "파일 형식이 주어지지 않았습니다."),
	PictureGenerateRequestNotFound(HttpStatus.NOT_FOUND, ErrorUtils.PictureGenerateRequestNotFound,
		"사진 생성 요청을 찾지 못했습니다."),
	NoPictureGenerateRequest(HttpStatus.NO_CONTENT, ErrorUtils.NoPictureGenerateRequest,
		"사진 생성요청이 없습니다."),
	UserNotFound(HttpStatus.NOT_FOUND, ErrorUtils.UserNotFound, "존재하지 않는 사용자입니다. [%s]"),
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
	WithDrawnUser(HttpStatus.BAD_REQUEST, ErrorUtils.WithDrawnUser, "탈퇴한 사용자입니다."),
	OnlyRequesterCanViewRequest(HttpStatus.FORBIDDEN, ErrorUtils.OnlyRequesterCanViewRequest,
		"사진생성요청을 요청한 유저만 볼 수 있습니다."),
	NotAllowedOauthProvider(HttpStatus.NOT_ACCEPTABLE, ErrorUtils.NotAllowedOauthProvider, "허가되지 않은 oauth type %s"),
	UserNotLoggedIn(HttpStatus.UNAUTHORIZED, ErrorUtils.UserNotLoggedIn, "로그아웃되었습니다. 다시 로그인해주세요"),
	PGRESStateException(HttpStatus.NOT_ACCEPTABLE, ErrorUtils.PGRESStateException,
		"현재 사진생성응답 status [%s] 해당 요청을 수행할 수 없습니다."),
	NoSettlementForWithdrawalException(HttpStatus.BAD_REQUEST, ErrorUtils.NoSettlementForWithdrawalException,
		"출금 가능한 정산 내역이 없습니다."),
	AlreadyCompletedResponse(HttpStatus.BAD_REQUEST, ErrorUtils.AlreadyCompletedRequest, "이미 완료된 응답을 수정할 수 없습니다."),
	WithdrawRequestNotFound(HttpStatus.NOT_FOUND, ErrorUtils.WithdrawRequestNotFound, "해당 출금 요청을 찾을 수 없습니다."),
	NotEnoughBalance(HttpStatus.NOT_ACCEPTABLE, ErrorUtils.NotEnoughBalance, "해당 공급자의 출금가능 잔액이 부족하여 요청을 완료할 수 없습니다."),
	NoPendingUserVerificationPGREQ(HttpStatus.NOT_FOUND, ErrorUtils.NoPendingUserVerificationPGREQ, "현재 사용자의 확인 대기중인 사진생성요청이 없습니다.");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	@Override
	public String getMessage(Object... args) {
		return String.format(message, args);
	}

}