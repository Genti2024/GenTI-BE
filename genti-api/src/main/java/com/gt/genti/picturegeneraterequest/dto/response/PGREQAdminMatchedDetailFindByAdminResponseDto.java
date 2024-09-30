package com.gt.genti.picturegeneraterequest.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.user.model.Sex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "[PGREQ][Admin] 사진생성요청 조회 by 어드민 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQAdminMatchedDetailFindByAdminResponseDto {

	@Schema(description = "주문 생성일시")
	LocalDateTime createdAt;
	@Schema(description = "사진생성요청 DB id", example = "1")
	Long pictureGenerateRequestId;
	@Schema(description = "생성 요청한 사용자의 email", example = "example@google.com")
	String requesterEmail;
	@Schema(description = "사용자 성별", example = "남")
	Sex sex;
	@Schema(description = "주문 내용", example = "대통령처럼 많은 군중들 앞에서 양 팔을 들고 연설하고 있는 모습")
	String prompt;
	@Schema(description = "요청 사진 비율")
	PictureRatio pictureRatio;
	@Schema(description = "추천 프롬프트", example = "Standing before a large crowd like a president, addressing them with both arms raised in a speech, (presidential demeanor:1.3), (addressing crowd:1.4), (arms raised:1.3), (public speaking:1.3), (commanding presence:1.3), (crowd engagement:1.2), (authoritative stance:1.3), (political speech:1.2), (captivating audience:1.3), (leader-like charisma:1.2)")
	String promptAdvanced;
	@Schema(description = "구도 참고 사진", nullable = true)
	CommonPictureResponseDto posePicture;
	@Schema(description = "본인 인증 사진", nullable = true)
	CommonPictureResponseDto pictureUserVerification;
	@Schema(description = "사용자의 얼굴 사진")
	List<CommonPictureResponseDto> facePictureList;

	@Schema(description = "앵글", example = "눈높이에서 촬영")
	CameraAngle cameraAngle;
	@Schema(description = "프레임", example = "바스트샷(상반신)")
	ShotCoverage shotCoverage;
	@Schema(description = "응답 리스트(현재는 1개)")
	List<PGRESAdminMatchedDetailFindByAdminResponseDto> responseList;

	@Builder
	public PGREQAdminMatchedDetailFindByAdminResponseDto(Long pictureGenerateRequestId, String requesterEmail,
		Sex sex, String prompt, PictureRatio pictureRatio, String promptAdvanced, List<CommonPictureResponseDto> facePictureList,
		CommonPictureResponseDto posePicture, CommonPictureResponseDto pictureUserVerification,CameraAngle cameraAngle, ShotCoverage shotCoverage,
		LocalDateTime createdAt, List<PGRESAdminMatchedDetailFindByAdminResponseDto> responseList) {
		this.pictureGenerateRequestId = pictureGenerateRequestId;
		this.requesterEmail = requesterEmail;
		this.sex = sex;
		this.prompt = prompt;
		this.pictureRatio = pictureRatio;
		this.promptAdvanced = promptAdvanced;
		this.facePictureList = facePictureList;
		this.posePicture = posePicture;
		this.pictureUserVerification = pictureUserVerification;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.createdAt = createdAt;
		this.responseList = responseList;
	}
}
