package com.gt.genti.dto;

import com.gt.genti.domain.ResponseExample;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleWithPictureFindResponseDto {
	Long id;
	String url;
	String prompt;


	public ExampleWithPictureFindResponseDto(ResponseExample responseExample) {
		this.id = responseExample.getId();
		this.url = responseExample.getExamplePictureUrl();
		this.prompt = responseExample.getExamplePrompt();
	}
}
