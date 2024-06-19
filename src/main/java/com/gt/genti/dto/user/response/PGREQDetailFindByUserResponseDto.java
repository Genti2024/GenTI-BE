package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
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

	List<CommonPictureResponseDto> facePictureUrlList;
	CommonPictureResponseDto posePictureUrl;

	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;

	PictureGenerateRequestStatus requestStatus;

	LocalDateTime createdAt;
	PGRESFindByUserResponseDto pictureGenerateResponse;

	public PGREQDetailFindByUserResponseDto(PictureGenerateRequest pictureGenerateRequest) {
		this.id = pictureGenerateRequest.getId();
		this.prompt = pictureGenerateRequest.getPrompt();
		this.promptAdvanced = pictureGenerateRequest.getPromptAdvanced();
		if (!pictureGenerateRequest.getUserFacePictureList().isEmpty()) {
			this.facePictureUrlList = pictureGenerateRequest.getUserFacePictureList()
				.stream()
				.map(PictureUserFace::mapToCommonResponse)
				.toList();
		}

		if (pictureGenerateRequest.getPicturePose() != null) {
			this.posePictureUrl = pictureGenerateRequest.getPicturePose().mapToCommonResponse();
		}
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();

		PictureGenerateResponse lastResponse = pictureGenerateRequest.getResponseList()
			.stream()
			.filter(response ->
				response.getStatus() == PictureGenerateResponseStatus.SUBMITTED_FINAL
					|| response.getStatus() == PictureGenerateResponseStatus.COMPLETED)
			.min((res1, res2) -> res2.getModifiedAt().compareTo(res1.getModifiedAt()))
			.orElseThrow(() -> ExpectedException.withLogging(DefaultErrorCode.UnHandledException));
		this.pictureGenerateResponse = new PGRESFindByUserResponseDto(lastResponse);

	}
}
