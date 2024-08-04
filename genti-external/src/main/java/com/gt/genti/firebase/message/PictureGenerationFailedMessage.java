package com.gt.genti.firebase.message;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.event.NotificationEvent;

public record PictureGenerationFailedMessage(
	Message message) {

	public record Message(Data data, String token, Notification notification) {
	}

	public record Data(String notificationType) {

		public static Data from(final NotificationEvent notificationEvent) {
			return new Data(
				notificationEvent.getNotificationType().getType()
			);
		}
	}
}