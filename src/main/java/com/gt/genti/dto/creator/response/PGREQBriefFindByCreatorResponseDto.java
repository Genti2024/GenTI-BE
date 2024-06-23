package com.gt.genti.dto.creator.response;

import java.time.Duration;
import java.time.LocalDateTime;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.other.util.TimeUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class PGREQBriefFindByCreatorResponseDto {
	@Schema(name = "requestId")
	Long requestId;
	@Schema(name = "prompt")
	String prompt;
	@Schema(name = "cameraAngle")
	CameraAngle cameraAngle;
	@Schema(name = "shotCoverage")
	ShotCoverage shotCoverage;
	@Schema(name = "status")
	PictureGenerateRequestStatus status;
	@Schema(name = "remainTimeForAccept")
	String remainTimeForAccept;
	@Schema(name = "createdAt")
	LocalDateTime createdAt;

	public PGREQBriefFindByCreatorResponseDto(PictureGenerateRequest pgreq) {
		this.requestId = pgreq.getId();
		this.prompt = pgreq.getPrompt();
		this.cameraAngle = pgreq.getCameraAngle();
		this.shotCoverage = pgreq.getShotCoverage();
		this.status = pgreq.getPictureGenerateRequestStatus();
		this.createdAt = pgreq.getCreatedAt();
		Duration duration = Duration.between(LocalDateTime.now(),
			createdAt.plusMinutes(TimeUtils.ACCEPTABLE_TIME_MINUTE));
		this.remainTimeForAccept = TimeUtils.getTimeString(duration);
	}
}
