package com.gt.genti.dto.admin.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "어드민의 사진생성응답 자세히 조회 응답 dto")
@Getter
@NoArgsConstructor
public class PGRESDetailFindByAdminResponseDto {
	@Schema(description = "사진생성응답 응답 Dto", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "메모", example = "제일 먼저 할 것")
	String memo;
	@Schema(description = "공급자가 생성한 사진 리스트")
	List<CommonPictureResponseDto> pictureCreatedByCreatorList;
	@Schema(description = "어드민이 생성한 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(description = "사진생성응답 상태", example = "SUBMITTED_FINAL")
	PictureGenerateResponseStatus responseStatus;
	@Schema(description = "생성일시")
	LocalDateTime createdAt;

	public PGRESDetailFindByAdminResponseDto(PictureGenerateResponse pgres) {
		this.pictureGenerateResponseId = pgres.getId();
		this.memo = pgres.getMemo();
		this.pictureCompletedList = pgres.getCompletedPictureList()
			.stream()
			.map(PictureEntityUtils::toCommonResponse)
			.toList();
		this.pictureCreatedByCreatorList = pgres.getCreatedByCreatorPictureList()
			.stream()
			.map(PictureEntityUtils::toCommonResponse)
			.toList();
		this.responseStatus = pgres.getStatus();
		this.createdAt = pgres.getCreatedAt();
	}
}
