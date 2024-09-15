package com.gt.genti.firebase.event;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationType;

import lombok.Getter;

@Getter
public class UpdateNotificationEvent extends NotificationEvent {

	private UpdateNotificationEvent(Long receiverId, String title, String body
	) {
		super(receiverId, null, NotificationType.SUCCESS, new Notification(title, body));
	}

	public static UpdateNotificationEvent of(Long receiverId, String title, String body) {
		return new UpdateNotificationEvent(receiverId, title, body);
	}
}