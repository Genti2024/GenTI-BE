package com.gt.genti.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureGenerateRequestDetailResponseDto {
	Long id;
	Long requesterId;
	String prompt;
	String advancedPrompt;
	List<String> facePictureUrlList;

	Long pictureCreatedByCreatorId;
	String pictureCreatedByCreatorUrl;

	Long pictureCompletedId;
	String pictureCompletedUrl;

	Long posePictureId;
	String posePictureUrl;

	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureGenerateRequestStatus requestStatus;
	PictureGenerateResponseStatus responseStatus;
	LocalDateTime createdAt;

	public PictureGenerateRequestDetailResponseDto(PictureGenerateRequest pictureGenerateRequest) {
		this.id = pictureGenerateRequest.getId();
		this.requesterId = pictureGenerateRequest.getRequester().getId();
		this.prompt = pictureGenerateRequest.getPrompt();
		this.facePictureUrlList = pictureGenerateRequest.getUserFacePictureList()
			.stream()
			.map(PictureUserFace::getUrl)
			.toList();
		this.posePictureId = pictureGenerateRequest.getPicturePose().getId();
		this.posePictureUrl = pictureGenerateRequest.getPicturePose().getUrl();
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();
	}

	public PictureGenerateRequestDetailResponseDto(Long id, Long requesterId, String prompt,
		List<String> facePictureUrlList, Long posePictureId, String posePictureUrl, CameraAngle cameraAngle,
		ShotCoverage shotCoverage, PictureGenerateRequestStatus requestStatus, LocalDateTime createdAt) {
		this.id = id;
		this.requesterId = requesterId;
		this.prompt = prompt;
		this.facePictureUrlList = facePictureUrlList;
		this.posePictureId = posePictureId;
		this.posePictureUrl = posePictureUrl;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.requestStatus = requestStatus;
		this.createdAt = createdAt;
	}
}
