package com.gt.genti.firebase.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {

	CANCELED("CANCELED"), SUCCESS("SUCCESS"), OPENCHAT("OPENCHAT"), HOME("HOME");

	private final String type;
}