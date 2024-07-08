package com.gt.genti.picturegeneraterequest.dto.request;

import java.util.List;

import com.gt.genti.picturegeneraterequest.model.CameraAngle;
import com.gt.genti.picture.PictureRatio;
import com.gt.genti.picturegeneraterequest.model.ShotCoverage;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "사진생성요청 생성 요청 dto")
public class PGREQSaveRequestDto {
	@NotBlank
	@Schema(description = "프롬프트", example = "벚꽃길에서 벤치에 앉아있는 사진이요")
	String prompt;

	@Schema(description = "포즈 사진")
	CommonPictureKeyUpdateRequestDto posePicture;

	@NotNull
	@Size(max = 3, min = 1, message = "사용자의 얼굴 사진 개수는 최소 1개, 최대 3개입니다.")
	@Schema(description = "얼굴 사진 리스트")
	List<@NotNull CommonPictureKeyUpdateRequestDto> facePictureList;

	@NotNull
	@Schema(description = "카메라 앵글")
	CameraAngle cameraAngle;
	@NotNull
	@Schema(description = "프레임")
	ShotCoverage shotCoverage;
	@NotNull
	@Schema(description = "사진 비율")
	PictureRatio pictureRatio;

	@Builder
	public PGREQSaveRequestDto(String prompt, CommonPictureKeyUpdateRequestDto posePicture,
		List<@NotNull CommonPictureKeyUpdateRequestDto> facePictureList, CameraAngle cameraAngle,
		ShotCoverage shotCoverage,
		PictureRatio pictureRatio) {
		this.prompt = prompt;
		this.posePicture = posePicture;
		this.facePictureList = facePictureList;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.pictureRatio = pictureRatio;
	}

	public PGREQSaveCommand toCommand() {
		return PGREQSaveCommand.builder()
			.prompt(this.prompt)
			.posePictureKey(this.posePicture.getKey())
			.cameraAngle(this.cameraAngle)
			.shotCoverage(this.shotCoverage)
			.pictureRatio(this.pictureRatio)
			.facePictureKeyList(this.facePictureList.stream().map(CommonPictureKeyUpdateRequestDto::getKey).toList())
			.build();
	}

}
