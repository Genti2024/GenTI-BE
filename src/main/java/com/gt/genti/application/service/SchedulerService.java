package com.gt.genti.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
	private final RequestMatchService requestMatchService;

	@Value("${schedule.delay.match-picture-generate-schedule}")
	private Long matchPictureGenerateScheduleDelay;

	@PostConstruct
	public void init() {
		log.info(" \n\n [SchedulerConfiguration] initialized");
		log.info("matchPictureGenerateScheduleDelay : " + matchPictureGenerateScheduleDelay);
		log.info("\n\n");
	}

	@Scheduled(fixedDelayString = "${schedule.delay.match-picture-generate-schedule}")
	public void run() {
		requestMatchService.matchPictureGenerateRequests();
	}
}
