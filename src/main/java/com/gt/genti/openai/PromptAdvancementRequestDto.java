package com.gt.genti.openai;

public record PromptAdvancementRequestDto(
	String model,
	String role,
	String content
) {
	public PromptAdvancementRequestDto(String content){
		this("gpt-3.5-turbo", "user", content);
	}
}
