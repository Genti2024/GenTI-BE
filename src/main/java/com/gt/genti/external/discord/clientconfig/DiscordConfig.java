package com.gt.genti.external.discord.clientconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.gt.genti.external.discord.controller.DiscordController;

@Configuration
public class DiscordConfig {

	@Value("${discord.webhook-url.base}")
	String baseUrl;

	@Bean
	public RestClient discordRestClient() {
		return RestClient.builder()
			.baseUrl(baseUrl)
			.defaultHeader("Content-type", "application/json; utf-8")
			.build();
	}

	@Bean
	public DiscordController discordController() {
		return new DiscordController(discordRestClient());
	}
}
