package com.gt.genti.firebase.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.firebase.dto.request.FcmTokenSaveOrUpdateRequestDto;
import com.gt.genti.firebase.service.FcmTokenRegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FcmTokenRegisterController {

	private final FcmTokenRegisterService fcmTokenRegisterService;

	@PostMapping("/notifications/token")
	public void createFcmToken(@RequestBody final FcmTokenSaveOrUpdateRequestDto fcmTokenSaveOrUpdateRequestDto) {
		fcmTokenRegisterService.registerFcmToken(fcmTokenSaveOrUpdateRequestDto);
	}
}