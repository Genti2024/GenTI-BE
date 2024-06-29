package com.gt.genti.picturegeneraterequest.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESFindByUserResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저가 사진생성요청에 대해 자세히 조회했을 때 응답 dto")
@Getter
@NoArgsConstructor
public class PGREQDetailFindByUserResponseDto {

	@Schema(description = "사진생성요청 Id", example = "1")
	Long id;
	@Schema(description = "프롬프트", example = "벚꽃길에서 벤치에 앉아있어요")
	String prompt;
	@Schema(description = "유저의 얼굴사진 3개")
	List<CommonPictureResponseDto> facePictureList;

	@Schema(description = "포즈사진 응답")
	CommonPictureResponseDto posePicture;
	@Schema(description = "내가 요청했던 앵글")
	CameraAngle cameraAngle;
	@Schema(description = "내가 요청했던 프레임")
	ShotCoverage shotCoverage;
	@Schema(description = "현재 내 요청의 진행 상태")
	PictureGenerateRequestStatus requestStatus;
	@Schema(description = "생성일시")
	LocalDateTime createdAt;
	@Schema(description = "사진생성응답 dto")
	PGRESFindByUserResponseDto pictureGenerateResponse;

	public PGREQDetailFindByUserResponseDto(PictureGenerateRequest pictureGenerateRequest) {
		this.id = pictureGenerateRequest.getId();
		this.prompt = pictureGenerateRequest.getPrompt();
		if (!pictureGenerateRequest.getUserFacePictureList().isEmpty()) {
			this.facePictureList = pictureGenerateRequest.getUserFacePictureList()
				.stream()
				.map(CommonPictureResponseDto::of)
				.toList();
		}

		this.posePicture = CommonPictureResponseDto.of(pictureGenerateRequest.getPicturePose());
		this.cameraAngle = pictureGenerateRequest.getCameraAngle();
		this.shotCoverage = pictureGenerateRequest.getShotCoverage();
		this.requestStatus = pictureGenerateRequest.getPictureGenerateRequestStatus();
		this.createdAt = pictureGenerateRequest.getCreatedAt();

		pictureGenerateRequest.getResponseList()
			.stream()
			.filter(response ->
				response.getStatus() == PictureGenerateResponseStatus.SUBMITTED_FINAL
					|| response.getStatus() == PictureGenerateResponseStatus.COMPLETED)
			.min((res1, res2) -> res2.getModifiedAt().compareTo(res1.getModifiedAt()))
			.ifPresent(generateResponse ->
				this.pictureGenerateResponse = new PGRESFindByUserResponseDto(generateResponse));

	}
}
