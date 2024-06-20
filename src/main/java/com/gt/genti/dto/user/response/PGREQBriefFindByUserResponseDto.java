package com.gt.genti.dto.user.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.domain.enums.converter.db.EnumUtil;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGREQBriefFindByUserResponseDto {
	Long requestId;
	String prompt;
	CameraAngle cameraAngle;
	ShotCoverage shotCoverage;
	PictureGenerateRequestStatus status;
	List<CommonPictureResponseDto> pictureCompletedList;
	LocalDateTime createdAt;

	public PGREQBriefFindByUserResponseDto(PictureGenerateRequest pgreq) {

		this.requestId = pgreq.getId();
		this.prompt = pgreq.getPrompt();
		this.cameraAngle = pgreq.getCameraAngle();
		this.shotCoverage = pgreq.getShotCoverage();
		this.status = pgreq.getPictureGenerateRequestStatus();
		this.createdAt = pgreq.getCreatedAt();
		if (!pgreq.getResponseList().isEmpty()) {
			pgreq.getResponseList()
				.stream()
				.filter(pgres -> EnumUtil.canUserSeePicture(pgres.getStatus()))
				.min((pgres1, pgres2) -> pgres2.getModifiedAt().compareTo(pgres1.getModifiedAt()))
				.ifPresent(lastPGRES -> this.pictureCompletedList = lastPGRES.getCompletedPictureList()
					.stream()
					.map(PictureEntity::mapToCommonResponse)
					.toList());
		}

		// Duration duration = Duration.between(LocalDateTime.now(),
		// 	createdAt.plusMinutes(TimeUtils.ACCEPTABLE_TIME_MINUTE));
		// this.remainTime = TimeUtils.getTimeString(duration);
	}
}
