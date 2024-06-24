package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "어드민의 사진생성응답 담당자 변경요청에 의한 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESUpdateAdminInChargeResponseDto {
	@Schema(description = "사진생성응답 DB Id", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "사진생성응답의 상태", example = "ADMIN_BEFORE_WORK")
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
