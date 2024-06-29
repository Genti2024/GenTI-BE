package com.gt.genti.openai.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.openai.dto.PromptAdvancementRequestCommand;
import com.gt.genti.openai.service.OpenAIService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenAIController {
	private final OpenAIService openAIService;

	@GetMapping("/test/gpt")
	public ResponseEntity<ApiResult<String>> gptTest(@RequestParam(value = "prompt", required = true) String prompt) {
		return success(openAIService.getAdvancedPrompt(new PromptAdvancementRequestCommand(prompt)));
	}
}
