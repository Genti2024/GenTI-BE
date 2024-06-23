package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor
public class PGREQDetailFindByUserResponseDto {

	@Schema(name = "id")
	Long id;
	@Schema(name = "prompt")
	String prompt;
	@Schema(name = "promptAdvanced")
	String promptAdvanced;
	@Schema(name = "facePictureUrlList")
	List<CommonPictureResponseDto> facePictureUrlList;
	@Schema(name = "posePictureUrl")
	CommonPictureResponseDto posePictureUrl;
	@Schema(name = "cameraAngle")
	CameraAngle cameraAngle;
	@Schema(name = "shotCoverage")
	ShotCoverage shotCoverage;
	@Schema(name = "requestStatus")
	PictureGenerateRequestStatus requestStatus;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;
	@Schema(name = "pictureGenerateResponse")
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

		pictureGenerateRequest.getResponseList()
			.stream()
			.filter(response ->
				response.getStatus() == PictureGenerateResponseStatus.SUBMITTED_FINAL
					|| response.getStatus() == PictureGenerateResponseStatus.COMPLETED)
			.min((res1, res2) -> res2.getModifiedAt().compareTo(res1.getModifiedAt()))
			.ifPresent(generateResponse ->
				this.pictureGenerateResponse = new PGRESFindByUserResponseDto(generateResponse));

	}
}
