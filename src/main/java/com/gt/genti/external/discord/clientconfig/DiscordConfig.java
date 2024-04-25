package com.gt.genti.external.discord.clientconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.gt.genti.external.discord.restclient.DiscordRestClient;

@Configuration
public class DiscordConfig {

	@Value("${discord.webhook-url.event}")
	String url;

	@Bean
	public DiscordRestClient discordRestClient() {
		RestClient restClient = RestClient.builder()
			.baseUrl(url)
			.defaultHeader("Content-type", "application/json; utf-8")
			.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
		return factory.createClient(DiscordRestClient.class);
	}
}
