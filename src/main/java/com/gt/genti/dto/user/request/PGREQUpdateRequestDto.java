package com.gt.genti.dto.user.request;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQUpdateRequestDto {

	Long pictureGenerateRequestId;
	String prompt;
	String posePictureUrl;
	List<String> facePictureUrlList;
	String cameraAngle;
	String shotCoverage;

	@Builder
	public PGREQUpdateRequestDto(Long id, String prompt, String posePictureUrl, String cameraAngle,
		String shotCoverage) {
		this.pictureGenerateRequestId = id;
		this.prompt = prompt;
		this.posePictureUrl = posePictureUrl;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}

