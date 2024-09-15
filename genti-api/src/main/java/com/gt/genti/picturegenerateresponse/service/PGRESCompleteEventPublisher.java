package com.gt.genti.picturegenerateresponse.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.firebase.event.PictureGenerationSuccessNotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PGRESCompleteEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishPictureGenerateCompleteEvent(Long receiverId) {
		eventPublisher.publishEvent(PictureGenerationSuccessNotificationEvent.of(receiverId));
	}

}

