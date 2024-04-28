package com.gt.genti.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class DiscordLogTest {
	@Test
	void loggingError() {
		log.error("오류");
		log.warn("워닝");
		log.info("정보");
	}
}
