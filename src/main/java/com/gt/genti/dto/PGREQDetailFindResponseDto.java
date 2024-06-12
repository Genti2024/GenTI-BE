package com.gt.genti.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.dto.admin.response.PGRESDetailFindByAdminResponseDto;
import com.gt.genti.dto.common.response.CommonPictureUrlResponseDto;
import com.gt.genti.other.util.TimeUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQDetailFindResponseDto {
	Long id;
	Long requesterId;
	String requesterEmail;
	String prompt;
	String promptAdvanced;
	List<CommonPictureUrlResponseDto> facePictureList;
	CommonPictureUrlResponseDto posePicture;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureGenerateRequestStatus requestStatus;
	LocalDateTime createdAt;
	String remainTime;
	List<PGRESDetailFindByAdminResponseDto> responseList;

	public PGREQDetailFindResponseDto(PictureGenerateRequest pictureGenerateRequest) {
		this.id = pictureGenerateRequest.getId();
		this.requesterId = pictureGenerateRequest.getRequester().getId();
		this.requesterEmail = pictureGenerateRequest.getRequester().getEmail();
		this.prompt = pictureGenerateRequest.getPrompt();
		this.promptAdvanced = pictureGenerateRequest.getPromptAdvanced();
		this.facePictureList = pictureGenerateRequest.getUserFacePictureList()
			.stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
		this.posePicture = pictureGenerateRequest.getPicturePose().mapToCommonResponse();
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();
		if (!pictureGenerateRequest.getResponseList().isEmpty()) {
			this.responseList = pictureGenerateRequest.getResponseList()
				.stream()
				.map(PGRESDetailFindByAdminResponseDto::new)
				.toList();

			Duration duration = Duration.between(LocalDateTime.now(),
				pictureGenerateRequest.getCreatedAt().plusHours(TimeUtils.PGREQ_LIMIT_HOUR));
			if (!duration.isNegative()) {
				this.remainTime = TimeUtils.getTimeString(duration);
			}
		}

	}
}
