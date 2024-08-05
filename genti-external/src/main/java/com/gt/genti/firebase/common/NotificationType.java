package com.gt.genti.firebase.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {

  PICTURE_GENERATION_FAILED("PICTURE_GENERATION_FAILED"),
  PICTURE_GENERATION_COMPLETED("PICTURE_GENERATION_COMPLETED");

  private final String type;
}