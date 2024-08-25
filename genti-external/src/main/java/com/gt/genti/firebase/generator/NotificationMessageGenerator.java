package com.gt.genti.firebase.generator;

public interface NotificationMessageGenerator {

	String makeMessage(
		final String targetToken
	);
}