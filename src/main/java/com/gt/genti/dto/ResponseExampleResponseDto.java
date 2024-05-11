package com.gt.genti.dto;

import com.gt.genti.domain.ResponseExample;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseExampleResponseDto {
	Long id;
	String url;
	String prompt;


	public ResponseExampleResponseDto(ResponseExample responseExample) {
		this.id = responseExample.getId();
		this.url = responseExample.getExamplePictureUrl();
		this.prompt = responseExample.getExamplePrompt();
	}
}
