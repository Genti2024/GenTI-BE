package com.gt.genti.external.aws.dto;

import com.gt.genti.dto.enums.PictureType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreSignedUrlRequestDto {
	PictureType pictureType;
	String serverImageName;

	@Builder

	public PreSignedUrlRequestDto(PictureType pictureType, String serverImageName) {
		this.pictureType = pictureType;
		this.serverImageName = serverImageName;
	}
}
