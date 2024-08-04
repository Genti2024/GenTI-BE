package com.gt.genti.test;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.firebase.event.PictureGenerationCompletedNotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishTest1() {
		eventPublisher.publishEvent(PictureGenerationCompletedNotificationEvent.of(2L));
	}

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishPictureGenerateCompleteEvent() {
		eventPublisher.publishEvent(PictureGenerationCompletedNotificationEvent.of(2L));
	}

}

