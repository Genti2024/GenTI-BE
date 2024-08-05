package com.gt.genti.firebase.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.gt.genti.firebase.client.FirebaseCloudMessageClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

	private final FirebaseCloudMessageClient firebaseCloudMessageClient;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener
	public void createNotification(final NotificationEvent notificationEvent) {
		firebaseCloudMessageClient.sendMessageTo(
			notificationEvent
		);
	}
}