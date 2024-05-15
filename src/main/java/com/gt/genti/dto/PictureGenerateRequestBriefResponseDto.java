package com.gt.genti.dto;

import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureGenerateRequestBriefResponseDto {
	Long requestId;
	String prompt;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	List<String> urlList;
	PictureGenerateRequestStatus status;

	@Builder
	public PictureGenerateRequestBriefResponseDto(Long requestId, String prompt, CameraAngle cameraAngle,
		ShotCoverage shotCoverage, List<String> urlList, PictureGenerateRequestStatus status) {
		this.requestId = requestId;
		this.prompt = prompt;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.urlList = urlList;
		this.status = status;
	}
}
