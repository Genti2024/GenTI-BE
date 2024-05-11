package com.gt.genti.dto;

import com.gt.genti.domain.ResponseExample;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromptOnlyExampleResponseDto {
	Long id;
	String prompt;

	public PromptOnlyExampleResponseDto(ResponseExample responseExample) {
		this.id = responseExample.getId();
		this.prompt = responseExample.getExamplePrompt();
	}
}
