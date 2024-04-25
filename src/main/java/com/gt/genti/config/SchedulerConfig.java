package com.gt.genti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gt.genti.scheduler.ScheduledService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {
	private final ScheduledService scheduledService;

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
		scheduledService.matchPictureGenerateRequests();
	}
}
