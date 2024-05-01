package com.gt.genti.external.discord.service;

import org.springframework.stereotype.Service;

import com.gt.genti.other.aop.annotation.DeployOnly;
import com.gt.genti.external.discord.restclient.DiscordRestClient;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscordService {

	private final DiscordRestClient discordRestClient;
	public static final String jsonTemplate = "{\"username\": \"genti-event\", \"content\": \"$1\"}";
	private StringBuilder sb;

	@PostConstruct
	void init() {
		sb = new StringBuilder(jsonTemplate);
	}

	@DeployOnly
	public void sendToDiscord(String message) {

		int startIndex = sb.lastIndexOf("$1");
		sb.replace(startIndex, startIndex + "$1".length(), message);
		discordRestClient.sendToDiscord(sb.toString());
		init();
	}
}
