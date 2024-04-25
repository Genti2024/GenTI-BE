package com.gt.genti.external.discord.restclient;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@Component
@HttpExchange
public interface DiscordRestClient {

	@PostExchange("")
	void sendToDiscord(@RequestBody String content);
}
