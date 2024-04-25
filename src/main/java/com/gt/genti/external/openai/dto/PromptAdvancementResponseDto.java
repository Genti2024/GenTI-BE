package com.gt.genti.external.openai.dto;

public record PromptAdvancementResponseDto(
	String role,
	String content
) {
	public PromptAdvancementResponseDto(String content){
		this("user", content);
	}
}
