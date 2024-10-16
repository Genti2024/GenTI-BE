package com.gt.genti.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.admin.api.PushNotificationApi;
import com.gt.genti.admin.dto.request.SendPushRequestDto;
import com.gt.genti.admin.service.PushNotificationService;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/push")
public class PushNotificationController implements PushNotificationApi {

	private final PushNotificationService pushNotificationService;

	@Value("${openchat.admin-secret-key}")
	private String ADMIN_SECRET_KEY;

	@Override()
	@PostMapping("/update")
	public ResponseEntity<GentiResponse.ApiResult<Boolean>> sendUpdatePush(
		@RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
		@RequestBody SendPushRequestDto sendPushRequestDto
	) {
		if (!ADMIN_SECRET_KEY.equals(adminSecretKey)) {
			throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
		} else {
			pushNotificationService.sendUpdatePush(sendPushRequestDto);
			return GentiResponse.success(true);
		}
	}

	@Override
	@PostMapping("/test")
	public ResponseEntity<GentiResponse.ApiResult<Boolean>> test(
		@RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
		@RequestBody SendPushRequestDto sendPushRequestDto,
		@RequestParam(name = "email") String email
	) {
		if (!ADMIN_SECRET_KEY.equals(adminSecretKey)) {
			throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
		} else {
			pushNotificationService.test(sendPushRequestDto, email);
			return GentiResponse.success(true);
		}
	}
}
