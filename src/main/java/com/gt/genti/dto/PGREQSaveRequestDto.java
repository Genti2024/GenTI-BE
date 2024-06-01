package com.gt.genti.dto;

import java.util.List;

import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.ShotCoverage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQSaveRequestDto {

	String prompt;
	String posePictureUrl;
	List<String> facePictureUrlList;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	@Builder
	public PGREQSaveRequestDto(String prompt, String posePictureUrl, CameraAngle cameraAngle, ShotCoverage shotCoverage, List<String> facePictureUrlList) {
		this.prompt = prompt;
		this.posePictureUrl = posePictureUrl;
		this.facePictureUrlList = facePictureUrlList;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}
