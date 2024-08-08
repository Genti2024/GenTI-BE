package com.gt.genti.user.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class UserDeleteScheduler {

	private final UserService userService;

	@Scheduled(cron = "0 0 2 * * ?")
	public void deleteUser() {
		userService.softDeleteUser(LocalDateTime.now());
	}


}
