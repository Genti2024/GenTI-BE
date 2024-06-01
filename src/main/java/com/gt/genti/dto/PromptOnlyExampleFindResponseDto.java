package com.gt.genti.dto;

import com.gt.genti.domain.ResponseExample;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromptOnlyExampleFindResponseDto {
	Long id;
	String prompt;

	public PromptOnlyExampleFindResponseDto(ResponseExample responseExample) {
		this.id = responseExample.getId();
		this.prompt = responseExample.getExamplePrompt();
	}
}
