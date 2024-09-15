package com.gt.genti.admin.service;

import org.springframework.stereotype.Service;

import com.gt.genti.admin.CustomEventPublisher;
import com.gt.genti.admin.dto.request.SendPushRequestDto;
import com.gt.genti.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationService {
	private final UserRepository userRepository;
	private final CustomEventPublisher customEventPublisher;

	public void sendUpdatePush(SendPushRequestDto sendPushRequestDto) {
		userRepository.findUpdatePushTargetUsers()
			.forEach(u -> customEventPublisher.publishCustomEvent(u.getId(), sendPushRequestDto.getTitle(),
				sendPushRequestDto.getBody()));
	}

	public void test(SendPushRequestDto sendPushRequestDto, String email) {
		userRepository.findByEmail(email).ifPresent(
			u -> customEventPublisher.publishCustomEvent(u.getId(), sendPushRequestDto.getTitle(),
				sendPushRequestDto.getBody())
		);
	}
}
