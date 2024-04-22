package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureGenerateRequestBriefResponseDto {
	Long requestId;
	String prompt;
	String cameraAngle;
	String shotCoverage;

	@Builder
	public PictureGenerateRequestBriefResponseDto(Long requestId, String prompt, String cameraAngle,
		String shotCoverage) {
		this.requestId = requestId;
		this.prompt = prompt;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}
