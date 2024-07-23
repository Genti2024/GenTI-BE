package com.gt.genti.picturegeneraterequest.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.discord.event.MatchEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void publishSignUpEvent(GentiMatchResult gentiMatchResult) {
		eventPublisher.publishEvent(MatchEvent.of(
			gentiMatchResult.getSummary(),
			gentiMatchResult.getMatchResultList()
		));
	}

}
