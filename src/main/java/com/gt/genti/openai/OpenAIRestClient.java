package com.gt.genti.openai;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@Component
@HttpExchange
public interface OpenAIRestClient {

	@PostExchange("/v1/chat/completions")
	String getAdvancedPrompt(@RequestBody String dto);
}
