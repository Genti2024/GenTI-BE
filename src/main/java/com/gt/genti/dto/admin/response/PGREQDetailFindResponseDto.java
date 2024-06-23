package com.gt.genti.dto.admin.response;

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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQDetailFindResponseDto {

	@Schema(name = "id")
	Long id;
	@Schema(name = "requesterId")
	Long requesterId;
	@Schema(name = "requesterEmail")
	String requesterEmail;
	@Schema(name = "prompt")
	String prompt;
	@Schema(name = "promptAdvanced")
	String promptAdvanced;
	@Schema(name = "facePictureList")
	List<CommonPictureResponseDto> facePictureList;
	@Schema(name = "posePicture")
	CommonPictureResponseDto posePicture;
	@Schema(name = "cameraAngle")
	CameraAngle cameraAngle;
	@Schema(name = "shotCoverage")
	ShotCoverage shotCoverage;
	@Schema(name = "requestStatus")
	PictureGenerateRequestStatus requestStatus;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;
	@Schema(name = "remainTime")
	String remainTime;
	@Schema(name = "responseList")
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
			} else {
				this.remainTime = TimeUtils.getZeroTime();
			}
		}

	}
}
