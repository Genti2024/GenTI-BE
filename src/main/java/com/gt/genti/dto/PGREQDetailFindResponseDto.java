package com.gt.genti.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.dto.admin.response.PGRESDetailFindByAdminResponseDto;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.other.util.PictureEntityUtils;
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
	List<CommonPictureResponseDto> facePictureList;
	CommonPictureResponseDto posePicture;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureGenerateRequestStatus requestStatus;
	LocalDateTime createdAt;
	String remainTime;
	List<PGRESDetailFindByAdminResponseDto> responseList;

	public PGREQDetailFindResponseDto(PictureGenerateRequest pgreq) {
		this.id = pgreq.getId();
		this.requesterId = pgreq.getRequester().getId();
		this.requesterEmail = pgreq.getRequester().getEmail();
		this.prompt = pgreq.getPrompt();
		this.promptAdvanced = pgreq.getPromptAdvanced();
		this.facePictureList = pgreq.getUserFacePictureList()
			.stream()
			.map(PictureEntityUtils::toCommonResponse)
			.toList();
		this.posePicture = PictureEntityUtils.toCommonResponse(pgreq.getPicturePose());
		this.cameraAngle = pgreq.getCameraAngle();
		this.shotCoverage = pgreq.getShotCoverage();
		this.requestStatus = pgreq.getPictureGenerateRequestStatus();
		this.createdAt = pgreq.getCreatedAt();
		if (!pgreq.getResponseList().isEmpty()) {
			this.responseList = pgreq.getResponseList()
				.stream()
				.map(PGRESDetailFindByAdminResponseDto::new)
				.toList();

			Duration duration = Duration.between(LocalDateTime.now(),
				pgreq.getCreatedAt().plusHours(TimeUtils.PGREQ_LIMIT_HOUR));
			if (!duration.isNegative()) {
				this.remainTime = TimeUtils.getTimeString(duration);
			} else{
				this.remainTime = TimeUtils.getZeroTime();
			}
		}

	}
}
