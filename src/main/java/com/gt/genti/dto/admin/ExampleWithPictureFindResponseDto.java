package com.gt.genti.dto.admin;

import com.gt.genti.domain.ResponseExample;
import com.gt.genti.dto.common.CommonPictureUrlResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExampleWithPictureFindResponseDto {
	CommonPictureUrlResponseDto picture;
	String prompt;

	public ExampleWithPictureFindResponseDto(ResponseExample responseExample) {
		this.picture = responseExample.mapToCommonResponse();
		this.prompt = responseExample.getExamplePrompt();
	}
}
