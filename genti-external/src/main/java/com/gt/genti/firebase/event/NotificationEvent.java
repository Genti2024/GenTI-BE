package com.gt.genti.firebase.event;

import com.gt.genti.firebase.common.Notification;
import com.gt.genti.firebase.common.NotificationData;
import com.gt.genti.firebase.common.NotificationType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class NotificationEvent {
  private final Long receiverId;
  private final NotificationData notificationData;
  private final NotificationType notificationType;
  private final Notification notification;
}