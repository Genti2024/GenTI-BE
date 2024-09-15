package com.gt.genti.firebase.event;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationType;

import lombok.Getter;

@Getter
public class PictureGenerationCanceledNotificationEvent extends NotificationEvent {

	private static final Notification notification = new Notification("사진 생성중 오류가 발생했습니다.", "사진 생성중 오류가 발생했습니다.");

	private PictureGenerationCanceledNotificationEvent(Long receiverId) {
		super(receiverId, null, NotificationType.CANCELED, notification);
	}

	public static PictureGenerationCanceledNotificationEvent of(Long receiverId) {
		return new PictureGenerationCanceledNotificationEvent(receiverId);
	}

}