package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.dto.common.response.CommonPictureUrlResponseDto;
import com.gt.genti.error.DefaultErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGREQDetailFindByUserResponseDto {
	Long id;

	String prompt;
	String promptAdvanced;

	List<String> facePictureUrlList;
	String posePictureUrl;

	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;

	PictureGenerateRequestStatus requestStatus;

	LocalDateTime createdAt;

	List<CommonPictureUrlResponseDto> pictureCompletedList;

	public PGREQDetailFindByUserResponseDto(PictureGenerateRequest pictureGenerateRequest) {
		this.id = pictureGenerateRequest.getId();
		this.prompt = pictureGenerateRequest.getPrompt();
		this.promptAdvanced = pictureGenerateRequest.getPromptAdvanced();
		this.facePictureUrlList = pictureGenerateRequest.getUserFacePictureList()
			.stream()
			.map(PictureUserFace::getKey)
			.toList();
		this.posePictureUrl = pictureGenerateRequest.getPicturePose().getKey();
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();
		if (pictureGenerateRequest.getResponseList().size() == 1) {
			this.pictureCompletedList = pictureGenerateRequest.getResponseList()
				.get(0)
				.getCompletedPictureList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		} else {
			PictureGenerateResponse realResponse = pictureGenerateRequest.getResponseList()
				.stream()
				.filter(response ->
					response.getStatus() == PictureGenerateResponseStatus.SUBMITTED_FINAL
						|| response.getStatus() == PictureGenerateResponseStatus.COMPLETED)
				.findFirst().orElseThrow(() -> new ExpectedException(DefaultErrorCode.UnHandledException));
			this.pictureCompletedList = realResponse.getCompletedPictureList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList();
		}

	}
}
