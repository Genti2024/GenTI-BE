package com.gt.genti.openai.restclient;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.gt.genti.openai.dto.PromptAdvancementRequestDto;
import com.gt.genti.openai.dto.PromptAdvancementResponse;

@Component
@HttpExchange
public interface OpenAIRestClient {

	@PostExchange("/v1/chat/completions")
	PromptAdvancementResponse getAdvancedPrompt(@RequestBody PromptAdvancementRequestDto dto);
}
