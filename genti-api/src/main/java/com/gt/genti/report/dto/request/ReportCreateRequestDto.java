package com.gt.genti.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[Report][User] 신고 생성 요청 by 사용자 dto")
public class ReportCreateRequestDto {
	@NotNull
	@Schema(description = "신고할 사진생성응답 id", example = "1")
	Long pictureGenerateResponseId;

	@NotBlank
	@Schema(description = "신고내용", example = "발가락 사진이 왔어요")
	String content;
}
