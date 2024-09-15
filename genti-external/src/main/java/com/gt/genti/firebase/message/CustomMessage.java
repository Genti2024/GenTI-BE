package com.gt.genti.firebase.message;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.event.NotificationEvent;

public record CustomMessage(Message message) {

	public record Message(Data data, String token, Notification notification) {

		public static Message from(NotificationEvent event, String fcmToken) {
			return new Message(Data.from(event), fcmToken, event.getNotification());
		}
	}

	public record Data(String title, String body, String type) {

		public static Data from(final NotificationEvent notificationEvent) {
			return new Data(notificationEvent.getNotification().getTitle(),
				notificationEvent.getNotification().getBody(), notificationEvent.getNotificationType().getType());
		}
	}
}