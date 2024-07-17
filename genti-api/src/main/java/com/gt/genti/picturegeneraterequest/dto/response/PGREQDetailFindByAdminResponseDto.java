package com.gt.genti.picturegeneraterequest.dto.response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESDetailFindByAdminResponseDto;
import com.gt.genti.user.model.Sex;
import com.gt.genti.util.DateTimeUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGREQ][Admin] 사진생성요청 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQDetailFindByAdminResponseDto {

	@Schema(description = "주문 생성일시")
	LocalDateTime createdAt;
	@Schema(description = "사진생성요청 DB id", example = "1")
	Long pictureGenerateRequestId;
	// @Schema(description = "생성 요청한 사용자의 DB id", example = "1")
	// Long requesterId;
	@Schema(description = "생성 요청한 사용자의 email", example = "example@google.com")
	String requesterEmail;
	@Schema(description = "사용자 성별", example = "남")
	Sex sex;
	@Schema(description = "주문 내용", example = "대통령처럼 많은 군중들 앞에서 양 팔을 들고 연설하고 있는 모습")
	String prompt;
	@Schema(description = "추천 프롬프트", example = "Standing before a large crowd like a president, addressing them with both arms raised in a speech, (presidential demeanor:1.3), (addressing crowd:1.4), (arms raised:1.3), (public speaking:1.3), (commanding presence:1.3), (crowd engagement:1.2), (authoritative stance:1.3), (political speech:1.2), (captivating audience:1.3), (leader-like charisma:1.2)")
	String promptAdvanced;
	@Schema(description = "구도 참고 사진", nullable = true)
	CommonPictureResponseDto posePicture;
	@Schema(description = "사용자의 얼굴 사진")
	List<CommonPictureResponseDto> facePictureList;

	@Schema(description = "앵글", example = "눈높이에서 촬영")
	CameraAngle cameraAngle;
	@Schema(description = "프레임", example = "바스트샷(상반신)")
	ShotCoverage shotCoverage;
	@Schema(description = "상태(작업 대기, 작업 중, 완료)", example = "IN_PROGRESS")
	PictureGenerateRequestStatus requestStatus;

	@Schema(description = "작업완료까지 남은 시간")
	String remainTime;
	@Schema(description = "응답 리스트(현재는 1개)")
	List<PGRESDetailFindByAdminResponseDto> responseList;

	public PGREQDetailFindByAdminResponseDto(PictureGenerateRequest pgreq) {
		this.pictureGenerateRequestId = pgreq.getId();
		// this.requesterId = pgreq.getRequester().getId();
		this.requesterEmail = pgreq.getRequester().getEmail();
		this.prompt = pgreq.getPrompt();
		this.promptAdvanced = pgreq.getPromptAdvanced();
		this.facePictureList = pgreq.getUserFacePictureList()
			.stream()
			.map(CommonPictureResponseDto::of)
			.toList();
		this.posePicture = CommonPictureResponseDto.of(pgreq.getPicturePose());
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
				pgreq.getCreatedAt().plusHours(DateTimeUtil.PGREQ_LIMIT_HOUR));
			if (!duration.isNegative()) {
				this.remainTime = DateTimeUtil.getTimeString(duration);
			} else {
				this.remainTime = DateTimeUtil.getZeroTime();
			}
		}
	}

	@Builder
	public PGREQDetailFindByAdminResponseDto(Long pictureGenerateRequestId, Long requesterId, String requesterEmail,
		String prompt,
		String promptAdvanced, List<CommonPictureResponseDto> facePictureList, CommonPictureResponseDto posePicture,
		CameraAngle cameraAngle, ShotCoverage shotCoverage, PictureGenerateRequestStatus requestStatus,
		LocalDateTime createdAt, List<PGRESDetailFindByAdminResponseDto> responseList) {
		this.pictureGenerateRequestId = pictureGenerateRequestId;
		// this.requesterId = requesterId;
		this.requesterEmail = requesterEmail;
		this.prompt = prompt;
		this.promptAdvanced = promptAdvanced;
		this.facePictureList = facePictureList;
		this.posePicture = posePicture;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.requestStatus = requestStatus;
		if (createdAt != null) {
			this.createdAt = createdAt;
			Duration duration = Duration.between(LocalDateTime.now(),
				this.createdAt.plusHours(DateTimeUtil.PGREQ_LIMIT_HOUR));
			if (!duration.isNegative()) {
				this.remainTime = DateTimeUtil.getTimeString(duration);
			} else {
				this.remainTime = DateTimeUtil.getZeroTime();
			}
		}
		this.responseList = responseList;
	}
}
