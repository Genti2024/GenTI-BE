package com.gt.genti.firebase.event;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationType;

import lombok.Getter;

@Getter
public class PictureGenerationCompletedNotificationEvent extends NotificationEvent {

	private static final Notification notification = new Notification("사진 생성이 완료되었습니다.", "사진 생성이 완료되었습니다.");

	private PictureGenerationCompletedNotificationEvent( Long receiverId
	) {
		super(receiverId, null, NotificationType.PICTURE_GENERATION_COMPLETED, notification);
	}

	public static PictureGenerationCompletedNotificationEvent of(Long receiverId) {
		return new PictureGenerationCompletedNotificationEvent(receiverId);
	}
}