package com.gt.genti.picturegeneraterequest.service;

public enum PictureGenerateRequestCancellationReason {
	NO_MATCHING, // 12시간 동안 아무와도 매칭되지 않음
	SUPPLIER_DID_NOT_WORK, // 작업하기로 한 공급자가 6시간 동안 작업을 수행하지 않음
	SUPPLIER_EXIT, // 작업하기로 한 공급자가 회원 탈퇴함
	INVALID_PROMPT, // 부적절한 프롬프트
	INVALID_IMAGE; // 부적절한 사진
}
