package com.gt.genti.firebase.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.firebase.event.NotificationEvent;
import com.gt.genti.firebase.message.PictureGenerateCompleteMessage;
import com.gt.genti.firebase.common.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PictureGenerationCompletedMessageGenerator implements NotificationMessageGenerator {
	private final NotificationEvent event;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String makeMessage(
		final String targetDeviceIdToken,
		final Notification notification
	) {

		final PictureGenerateCompleteMessage pictureGenerateCompleteMessage = new PictureGenerateCompleteMessage(
			new PictureGenerateCompleteMessage.Message(notification, targetDeviceIdToken,
				PictureGenerateCompleteMessage.Data.from(event))
		);

		try {
			return objectMapper.writeValueAsString(pictureGenerateCompleteMessage);
		} catch (JsonProcessingException e) {
			throw ExpectedException.withLogging(ResponseCode.FCM_TOKEN_CONVERTING_JSON_ERROR);
		}
	}
}