package com.gt.genti.firebase.generator;

import static com.gt.genti.firebase.message.PictureGenerationFailedMessage.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.firebase.event.NotificationEvent;
import com.gt.genti.firebase.message.PictureGenerationFailedMessage;
import com.gt.genti.firebase.common.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PictureGenerationFailedMessageGenerator implements NotificationMessageGenerator {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final NotificationEvent notificationEvent;

	@Override
	public String makeMessage(
		final String targetToken,
		final Notification notification
	) {
		try {
			final PictureGenerationFailedMessage message = new PictureGenerationFailedMessage(
				new Message(Data.from(notificationEvent), targetToken, notification)
			);
			return objectMapper.writeValueAsString(message);

		} catch (JsonProcessingException e) {
			throw ExpectedException.withLogging(ResponseCode.FCM_TOKEN_CONVERTING_JSON_ERROR);
		}
	}
}