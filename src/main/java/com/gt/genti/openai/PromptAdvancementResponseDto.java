package com.gt.genti.openai;

public record PromptAdvancementResponseDto(
	String role,
	String content
) {
	public PromptAdvancementResponseDto(String content){
		this("user", content);
	}
}
