package com.gt.genti.picturegeneraterequest.dto.response;

import java.time.Duration;
import java.time.LocalDateTime;

import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.util.DateTimeUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공급자의 사진생성요청 간단히 조회 응답 dto")
@Getter
@NoArgsConstructor
public class PGREQBriefFindByCreatorResponseDto {
	@Schema(description = "사진생성요청 DB Id", example = "1")
	Long pictureGenerateRequestId;
	@Schema(description = "프롬프트", example = "배경은 저녁 파리에서 옷은 검정 항공잠바와 검정 조거 팬츠를 입고 자세는 서서 구경하는 모습이면 좋겠어")
	String prompt;
	@Schema(description = "앵글")
	CameraAngle cameraAngle;
	@Schema(description = "프레임")
	ShotCoverage shotCoverage;
	@Schema(description = "사진생성요청의 상태")
	PictureGenerateRequestStatus status;
	@Schema(description = "수락가능한 제한시간 HH:MM:SS", example = "00:07:42")
	String remainTimeForAccept;
	@Schema(description = "사진생성요청의 생성일시")
	LocalDateTime createdAt;

	public PGREQBriefFindByCreatorResponseDto(PictureGenerateRequest pgreq) {
		this.pictureGenerateRequestId = pgreq.getId();
		this.prompt = pgreq.getPrompt();
		this.cameraAngle = pgreq.getCameraAngle();
		this.shotCoverage = pgreq.getShotCoverage();
		this.status = pgreq.getPictureGenerateRequestStatus();
		this.createdAt = pgreq.getCreatedAt();
		Duration duration = Duration.between(LocalDateTime.now(),
			createdAt.plusMinutes(DateTimeUtil.ACCEPTABLE_TIME_MINUTE));
		this.remainTimeForAccept = DateTimeUtil.getTimeString(duration);
	}
}
