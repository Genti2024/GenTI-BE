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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저의 사진생성요청 간단히 조회 응답 dto")
@Getter
@NoArgsConstructor
public class PGREQBriefFindByUserResponseDto {
	@Schema(description = "사진생성요청 DB Id", example = "1")
	Long pictureGenerateRequestId;
	@Schema(description = "프롬프트", example = "담벼락 앞에서 브이")
	String prompt;
	@Schema(description = "앵글")
	CameraAngle cameraAngle;
	@Schema(description = "프레임")
	ShotCoverage shotCoverage;
	@Schema(description = "사진생성요청 상태")
	PictureGenerateRequestStatus status;
	@Schema(description = "완성된 사진 리스트")
	List<CommonPictureResponseDto> pictureCompletedList;
	@Schema(description = "생성일시")
	LocalDateTime createdAt;

	public PGREQBriefFindByUserResponseDto(PictureGenerateRequest pgreq) {

		this.pictureGenerateRequestId = pgreq.getId();
		this.prompt = pgreq.getPrompt();
		this.cameraAngle = pgreq.getCameraAngle();
		this.shotCoverage = pgreq.getShotCoverage();
		this.status = pgreq.getPictureGenerateRequestStatus();
		this.createdAt = pgreq.getCreatedAt();
		if (!pgreq.getResponseList().isEmpty()) {
			pgreq.getResponseList()
				.stream()
				.filter(pgres -> EnumUtil.PICTURE_CREATE_COMPLETED(pgres.getStatus()))
				.min((pgres1, pgres2) -> pgres2.getModifiedAt().compareTo(pgres1.getModifiedAt()))
				.ifPresent(lastPGRES -> this.pictureCompletedList = lastPGRES.getCompletedPictureList()
					.stream()
					.map(PictureEntity::mapToCommonResponse)
					.toList());
		}

		// Duration duration = Duration.between(LocalDateTime.now(),
		// 	createdAt.plusMinutes(DateTimeUtils.ACCEPTABLE_TIME_MINUTE));
		// this.remainTime = DateTimeUtils.getTimeString(duration);
	}
}
