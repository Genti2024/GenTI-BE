package com.gt.genti.dto.admin.request;

import com.gt.genti.command.admin.ExampleSaveCommand;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.other.valid.ValidKey;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "완성된 예시 업로드")

public class ExampleSaveRequestDto {
	@ValidKey
	@Schema(name = "key", description = "FileType Enum 값으로 시작하는 문자열", example = "USER_UPLOADED_IMAGE/**")
	String key;
	@NotBlank
	@Schema(name = "prompt", description = "완성된 사진을 생성하기 위해 요청한 프롬프트", example = "벚꽃길에서 교복입고 벤치에 앉아있는 사진이요")
	String prompt;
	@Schema(name = "pictureRatio", description = "요청하는 사진의 비율 enum")
	PictureRatio pictureRatio;

	public ExampleSaveCommand toCommand() {
		return ExampleSaveCommand.builder()
			.key(key)
			.prompt(prompt)
			.pictureRatio(pictureRatio)
			.build();
	}

}
