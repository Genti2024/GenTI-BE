package com.gt.genti.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureGenerateRequestModifyDto {

	Long pictureGenerateRequestId;
	String prompt;
	String posePictureUrl;
	String cameraAngle;
	String shotCoverage;

	@Builder
	public PictureGenerateRequestModifyDto(Long id, String prompt, String posePictureUrl, String cameraAngle,
		String shotCoverage) {
		this.pictureGenerateRequestId = id;
		this.prompt = prompt;
		this.posePictureUrl = posePictureUrl;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}

