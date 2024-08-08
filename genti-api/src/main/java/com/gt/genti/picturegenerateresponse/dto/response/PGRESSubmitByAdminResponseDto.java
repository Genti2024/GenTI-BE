package com.gt.genti.picturegenerateresponse.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][Admin] 사진생성응답 제출 by 어드민 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESSubmitByAdminResponseDto {
	@Schema(description = "사진생성응답 DB Id", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "소요시간 HH:MM:SS", example = "02:17:02")
	String elapsedTime;

	@Builder
	public PGRESSubmitByAdminResponseDto(Long pictureGenerateResponseId, String elapsedTime) {
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.elapsedTime = elapsedTime;
	}
}
