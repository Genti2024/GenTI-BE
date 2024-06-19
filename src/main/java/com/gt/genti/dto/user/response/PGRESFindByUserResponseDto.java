package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import lombok.Getter;

@Getter
public class PGRESFindByUserResponseDto {
	Long pictureGenerateResponseId;
	List<CommonPictureResponseDto> pictureCompletedList;

	public PGRESFindByUserResponseDto(PictureGenerateResponse pgres) {
		this.pictureGenerateResponseId = pgres.getId();
		this.pictureCompletedList = pgres.getCompletedPictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}
}
