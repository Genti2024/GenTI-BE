package com.gt.genti.responseexample.dto.response;

import com.gt.genti.picture.responseexample.model.ResponseExample;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[ResponseExample][Admin&User] 작업 예시 조회 응답 dto")
@Getter
@NoArgsConstructor
public class ExampleWithPictureFindResponseDto {
	@Schema(description = "예시 사진 응답")
	CommonPictureResponseDto picture;
	@Schema(description = "에시 프롬프트", example = "벚꽃길에서 벤치에 앉아있어요")
	String prompt;

	public ExampleWithPictureFindResponseDto(ResponseExample responseExample) {
		this.picture = CommonPictureResponseDto.of(responseExample);
		this.prompt = responseExample.getExamplePrompt();
	}
}
