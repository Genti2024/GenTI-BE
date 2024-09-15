package com.gt.genti.firebase.message;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.event.NotificationEvent;

public record PictureGenerateCompleteMessage(Message message) {

	public record Message(Notification notification, String token, Data data) {
		public static Message from(NotificationEvent event, String fcmToken) {
			return new Message(event.getNotification(), fcmToken, Data.from(event));
		}
	}

	public record Data(String title, String body, String type) {
		public static Data from(final NotificationEvent notificationEvent) {
			return new Data(notificationEvent.getNotification().getTitle(),
				notificationEvent.getNotification().getBody(),
				notificationEvent.getNotificationType().getType());
		}
	}
}