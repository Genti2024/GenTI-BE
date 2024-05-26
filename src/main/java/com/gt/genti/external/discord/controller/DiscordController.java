package com.gt.genti.external.discord.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.gt.genti.other.aop.annotation.DeployOnly;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiscordController {

	private final RestClient discordRestClient;
	public static final String CONTENT = "CONTENT";
	public static final String jsonTemplate = "{\"username\": \"genti-event\", \"content\": \"" + CONTENT + "\"}";
	private StringBuilder sb;

	@PostConstruct
	void init() {
		sb = new StringBuilder(jsonTemplate);
	}

	@Value("${discord.webhook-url.event}")
	String eventChannelUrl;

	@Value("${discord.webhook-url.admin}")
	String adminChannelUrl;

	public void sendToEventChannel(String content) {
		String body = getRequestBody(content);
		discordRestClient.post()
			.uri(eventChannelUrl)
			.body(body)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError,
				(req, res) -> log.error("discord event 채널에 메세지 송신중 오류 발생 \n " + res.getBody())
			)
			.toBodilessEntity();

	}

	// @DeployOnly
	public void sendToAdminChannel(String message) {
		String body = getRequestBody(message);
		discordRestClient.post()
			.uri(eventChannelUrl)
			.body(body)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError,
				(req, res) -> log.error("discord admin 채널에 메세지 송신중 오류 발생 \n " + res.getBody())
			)
			.toBodilessEntity();
	}

	@NotNull
	private String getRequestBody(String content) {
		int contentIndex = sb.lastIndexOf(CONTENT);
		sb.replace(contentIndex, contentIndex + CONTENT.length(), content);
		String body = sb.toString();
		init();
		return body;
	}

}
