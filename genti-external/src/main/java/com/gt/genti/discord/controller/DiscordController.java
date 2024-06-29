package com.gt.genti.discord.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.gt.genti.discord.dto.SendMessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiscordController {

	private final RestClient discordRestClient;
	private StringBuilder sb;

	@Value("${discord.webhook-url.base}")
	String baseUrl;
	@Value("${discord.webhook-url.event}")
	String eventChannelUrl;

	@Value("${discord.webhook-url.admin}")
	String adminChannelUrl;

	public void sendToEventChannel(String message) {
		discordRestClient.post()
			.uri(baseUrl + eventChannelUrl)
			.body(SendMessageDto.getEventMessage(message))
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError,
				(req, res) -> log.error("discord event 채널에 메세지 송신중 오류 발생 \n " + res.getBody())
			)
			.toBodilessEntity();

	}

	// @DeployOnly
	public void sendToAdminChannel(String message) {
		discordRestClient.post()
			.uri(baseUrl + eventChannelUrl)
			.body(SendMessageDto.getAdminMessage(message))
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError,
				(req, res) -> log.error("discord admin 채널에 메세지 송신중 오류 발생 \n " + res.getBody())
			)
			.toBodilessEntity();
	}

}
