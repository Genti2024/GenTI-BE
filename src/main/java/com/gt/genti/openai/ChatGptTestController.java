package com.gt.genti.openai;

import static com.gt.genti.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatGptTestController {
	private final ChatGptService chatGptService;

	@PostMapping("/test/gpt")
	public ResponseEntity<ApiResult<String>> gptTest(@RequestParam(value = "prompt", required = true) String prompt) {
		return success(chatGptService.getAdvancedPrompt(new PromptAdvancementRequestDto(prompt)));
	}
}
