package com.gt.genti.admin;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.firebase.event.UpdateNotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishCustomEvent(Long receiverId, String title, String body) {
		eventPublisher.publishEvent(UpdateNotificationEvent.of(receiverId, title, body));
	}

}