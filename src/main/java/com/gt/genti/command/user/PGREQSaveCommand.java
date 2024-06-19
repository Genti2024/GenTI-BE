package com.gt.genti.command.user;

import java.util.List;

import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PGREQSaveCommand {

	String prompt;
	String posePictureKey;
	List<String> facePictureKeyList;

	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureRatio pictureRatio;

	@Builder
	public PGREQSaveCommand(String prompt, String posePictureKey, List<String> facePictureKeyList,
		CameraAngle cameraAngle,
		ShotCoverage shotCoverage, PictureRatio pictureRatio) {
		this.prompt = prompt;
		this.posePictureKey = posePictureKey;
		this.facePictureKeyList = facePictureKeyList;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.pictureRatio = pictureRatio;
	}
}
