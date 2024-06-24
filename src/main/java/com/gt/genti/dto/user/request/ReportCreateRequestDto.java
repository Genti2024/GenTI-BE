package com.gt.genti.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "신고 생성 요청 dto")
public class ReportCreateRequestDto {
	@NotNull
	@Schema(description = "신고할 사진생성응답 id", example = "1")
	Long pictureGenerateResponseId;

	@NotBlank
	@Schema(description = "신고내용", example = "발가락 사진이 왔어요")
	String content;
}
