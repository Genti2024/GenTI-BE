package com.gt.genti.firebase.event;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationType;

import lombok.Getter;

@Getter
public class PictureGenerationSuccessNotificationEvent extends NotificationEvent {

	private static final Notification notification = new Notification("나만의 사진이 도착했어요!", "지금 당장 확인해보러 가실까요?");

	private PictureGenerationSuccessNotificationEvent( Long receiverId
	) {
		super(receiverId, null, NotificationType.SUCCESS, notification);
	}

	public static PictureGenerationSuccessNotificationEvent of(Long receiverId) {
		return new PictureGenerationSuccessNotificationEvent(receiverId);
	}
}