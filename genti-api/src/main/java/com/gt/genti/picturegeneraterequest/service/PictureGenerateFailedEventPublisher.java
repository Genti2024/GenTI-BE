package com.gt.genti.picturegeneraterequest.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.firebase.event.PictureGenerationFailedNotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateFailedEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishPictureGenerateFailedEvent(Long receiverId) {
		eventPublisher.publishEvent(PictureGenerationFailedNotificationEvent.of(receiverId));
	}

}

