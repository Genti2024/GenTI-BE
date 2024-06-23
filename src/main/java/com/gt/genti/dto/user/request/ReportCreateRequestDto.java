package com.gt.genti.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "ㅁㄴㅇㄹ")

public class ReportCreateRequestDto {
	@NotNull
	@Schema(name = "pictureGenerateResponseId")
	Long pictureGenerateResponseId;
	@NotBlank
	@Schema(name = "content")
	String content;
}
