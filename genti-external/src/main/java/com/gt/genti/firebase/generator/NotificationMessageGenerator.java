package com.gt.genti.firebase.generator;

import com.gt.genti.firebase.common.Notification;

public interface NotificationMessageGenerator {

  String makeMessage(
      final String targetToken,
      final Notification notification
  );
}