package com.gt.genti.firebase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.fcm.model.FcmToken;
import com.gt.genti.fcm.repository.FcmTokenRepository;
import com.gt.genti.firebase.dto.request.FcmTokenSaveOrUpdateRequestDto;
import com.gt.genti.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FcmTokenRegisterService {

	private final FcmTokenRepository fcmTokenRepository;
	private final UserRepository userRepository;

	public void registerFcmToken(final FcmTokenSaveOrUpdateRequestDto fcmTokenSaveOrUpdateRequestDto) {
		final Long memberId = fcmTokenSaveOrUpdateRequestDto.userId();
		final String token = fcmTokenSaveOrUpdateRequestDto.token();

		checkUserExists(memberId);
		fcmTokenRepository.findByUserId(memberId)
			.ifPresentOrElse(
				foundFcmToken -> foundFcmToken.update(token),
				() -> fcmTokenRepository.save(new FcmToken(token, memberId))
			);
	}

	private void checkUserExists(final Long userId) {
		if (!userRepository.existsById(userId)) {
			throw ExpectedException.withLogging(ResponseCode.UserNotFound, userId);
		}
	}
}