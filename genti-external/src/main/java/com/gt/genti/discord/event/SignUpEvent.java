package com.gt.genti.discord.event;

import java.time.LocalDateTime;

public record SignUpEvent(Long totalUserCount, String name, String email, String socialPlatform,
						  LocalDateTime createdAt) {

	public static SignUpEvent of(Long totalUserCount, String name, String email, String socialPlatform,
		LocalDateTime createdAt) {
		return new SignUpEvent(totalUserCount, name, email, socialPlatform, createdAt);
	}

}