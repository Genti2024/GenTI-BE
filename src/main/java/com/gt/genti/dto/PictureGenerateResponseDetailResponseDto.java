package com.gt.genti.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureGenerateResponseDetailResponseDto {
	Long id;
	String memo;
	List<CommonPictureResponseDto> pictureCreatedByCreatorList;
	List<CommonPictureResponseDto> pictureCompletedList;
	PictureGenerateResponseStatus responseStatus;
	LocalDateTime createdAt;

	public PictureGenerateResponseDetailResponseDto(PictureGenerateResponse pgres) {
		this.id = pgres.getId();
		this.memo = pgres.getMemo();
		this.pictureCompletedList = pgres.getCompletedPictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
		this.pictureCreatedByCreatorList = pgres.getCreatedByCreatorPictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
		this.responseStatus = pgres.getStatus();
		this.createdAt = pgres.getCreatedAt();
	}
}
