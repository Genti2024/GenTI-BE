package com.gt.genti.admin.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.admin.dto.request.SendPushRequestDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "[PushNotificationController] 푸시알림 컨트롤러", description = "백엔드용 푸시알림 보내기")
public interface PushNotificationApi {

	@Operation(summary = "업데이트 푸시알림 보내기", description = "OB, YB 유저 식별하여 전체 푸시알림")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
	})
	ResponseEntity<GentiResponse.ApiResult<Boolean>> sendUpdatePush(
		@RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
		@RequestBody SendPushRequestDto sendPushRequestDto
	);

	@Operation(summary = "푸시알림 테스트", description = "이메일 입력하여 해당 사용자에게 테스트")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
	})
	ResponseEntity<GentiResponse.ApiResult<Boolean>> test(
		@RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
		@RequestBody SendPushRequestDto sendPushRequestDto,
		@RequestParam(name = "email") String email
	);
}


