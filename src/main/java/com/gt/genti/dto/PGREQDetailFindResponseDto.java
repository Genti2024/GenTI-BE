package com.gt.genti.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureUserFace;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
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

	List<String> facePictureUrlList;

	Long posePictureId;
	String posePictureUrl;

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
		this.facePictureUrlList = pictureGenerateRequest.getUserFacePictureList()
			.stream()
			.map(PictureUserFace::getKey)
			.toList();
		this.posePictureId = pictureGenerateRequest.getPicturePose().getId();
		this.posePictureUrl = pictureGenerateRequest.getPicturePose().getKey();
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();
		if (!pictureGenerateRequest.getResponseList().isEmpty()) {
			this.responseList = pictureGenerateRequest.getResponseList()
				.stream()
				.map(d -> new PGRESDetailFindByAdminResponseDto(d))
				.toList();

			Duration duration = Duration.between(LocalDateTime.now(),
				pictureGenerateRequest.getCreatedAt().plusHours(TimeUtils.PGREQ_LIMIT_HOUR));
			if (!duration.isNegative()) {
				this.remainTime = TimeUtils.getTimeString(duration);
			}
		}

	}
}