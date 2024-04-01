package com.gt.genti.openai;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatGptService {

	private final OpenAIRestClient openAIRestClient;

	public String getAdvancedPrompt(PromptAdvancementRequestDto req) {

		return openAIRestClient.getAdvancedPrompt(req);
	}
}
