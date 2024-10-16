package com.gt.genti.picturegenerateresponse.dto.response;

import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGRES][Admin] 사진생성응답 담당 어드민 변경시 응답 Dto", description = "어드민의 사진생성응답 담당자 변경요청에 의한 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESUpdateAdminInChargeResponseDto {
	@Schema(description = "작업넘버(사진생성응답 DB Id)", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "작업(사진생성응답)의 상태", example = "ADMIN_BEFORE_WORK")
	PictureGenerateResponseStatus status;
	@Schema(description = "담당자 어드민 이름", example = "우기")
	String adminInCharge;

	@Builder
	public PGRESUpdateAdminInChargeResponseDto(Long pictureGenerateResponseId, PictureGenerateResponseStatus status,
		String adminInCharge) {
		this.pictureGenerateResponseId = pictureGenerateResponseId;
		this.status = status;
		this.adminInCharge = adminInCharge;
	}
}
