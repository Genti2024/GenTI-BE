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
@Schema
@Getter
@NoArgsConstructor
public class PGRESDetailFindByAdminResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "memo")
	String memo;
	@Schema(name = "pictureCreatedByCreatorList")
	List<CommonPictureResponseDto> pictureCreatedByCreatorList;
	@Schema(name = "pictureCompletedList")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(name = "responseStatus")
	PictureGenerateResponseStatus responseStatus;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;

	public PGRESDetailFindByAdminResponseDto(PictureGenerateResponse pgres) {
		this.id = pgres.getId();
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
