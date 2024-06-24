package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.ResponseExample;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "작업 예시 조회 응답 dto")
@Getter
@NoArgsConstructor
public class ExampleWithPictureFindResponseDto {
	@Schema(description = "예시 사진 응답")
	CommonPictureResponseDto picture;
	@Schema(description = "에시 프롬프트", example = "벚꽃길에서 벤치에 앉아있어요")
	String prompt;

	public ExampleWithPictureFindResponseDto(ResponseExample responseExample) {
		this.picture = PictureEntityUtils.toCommonResponse(responseExample);
		this.prompt = responseExample.getExamplePrompt();
	}
}
