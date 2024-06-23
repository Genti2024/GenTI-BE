package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.ResponseExample;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class ExampleWithPictureFindResponseDto {
	@Schema(name = "picture")
	CommonPictureResponseDto picture;
	@Schema(name = "prompt")
	String prompt;

	public ExampleWithPictureFindResponseDto(ResponseExample responseExample) {
		this.picture = responseExample.mapToCommonResponse();
		this.prompt = responseExample.getExamplePrompt();
	}
}
