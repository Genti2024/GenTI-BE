package com.gt.genti.dto.user.request;

import java.util.List;

import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQSaveRequestDto {

	@NotBlank
	String prompt;
	String posePictureKey;
	List<String> facePictureKeyList;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureRatio pictureRatio;
	@Builder
	public PGREQSaveRequestDto(String prompt, String posePictureKey, CameraAngle cameraAngle, ShotCoverage shotCoverage, List<String> facePictureKeyList) {
		this.prompt = prompt;
		this.posePictureKey = posePictureKey;
		this.facePictureKeyList = facePictureKeyList;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}
