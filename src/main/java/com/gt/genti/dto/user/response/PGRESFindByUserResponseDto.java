package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
public class PGRESFindByUserResponseDto {
	@Schema(name = "pictureGenerateResponseId")
	Long pictureGenerateResponseId;
	@Schema(name = "pictureCompletedList")
	List<CommonPictureResponseDto> pictureCompletedList;

	public PGRESFindByUserResponseDto(PictureGenerateResponse pgres) {
		this.pictureGenerateResponseId = pgres.getId();
		this.pictureCompletedList = pgres.getCompletedPictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}
}
