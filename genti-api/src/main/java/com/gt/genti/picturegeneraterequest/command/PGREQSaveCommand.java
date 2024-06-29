package com.gt.genti.picturegeneraterequest.command;

import java.util.List;

import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;

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
