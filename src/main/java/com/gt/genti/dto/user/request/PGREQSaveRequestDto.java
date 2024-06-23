package com.gt.genti.dto.user.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.other.valid.ValidEnum;
import com.gt.genti.other.valid.ValidKey;

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
@Schema(description = "ㅁㄴㅇㄹ")
public class PGREQSaveRequestDto {

	@NotBlank
	@JsonProperty("prompt")
	@Schema(name = "prompt")
	String prompt;
	@ValidKey
	@JsonProperty("posePictureKey")
	@Schema(name = "posePictureKey")
	String posePictureKey;

	@NotNull
	@Size(max = 3, min = 1, message = "사용자의 얼굴 사진 개수는 최소 1개, 최대 3개입니다.")
	@JsonProperty("facePictureKeyList")
	@Schema(name = "facePictureKeyList")
	List<@ValidKey @NotNull String> facePictureKeyList;

	@NotNull
	@ValidEnum(value = CameraAngle.class)
	@JsonProperty("cameraAngle")
	@Schema(name = "cameraAngle")
	String cameraAngle;
	@NotNull
	@ValidEnum(value = ShotCoverage.class)
	@JsonProperty("shotCoverage")
	@Schema(name = "shotCoverage")
	String shotCoverage;
	@NotNull
	@ValidEnum(value = PictureRatio.class)
	@JsonProperty("pictureRatio")
	@Schema(name = "pictureRatio")
	String pictureRatio;

	@Builder
	public PGREQSaveRequestDto(String prompt, String posePictureKey, String cameraAngle, String shotCoverage,
		List<String> facePictureKeyList, String pictureRatio) {
		this.prompt = prompt;
		this.posePictureKey = posePictureKey;
		this.facePictureKeyList = facePictureKeyList;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
		this.pictureRatio = pictureRatio;
	}

	public PGREQSaveCommand toCommand() {
		return PGREQSaveCommand.builder()
			.prompt(this.prompt)
			.posePictureKey(this.posePictureKey)
			.cameraAngle(CameraAngle.valueOf(this.cameraAngle))
			.shotCoverage(ShotCoverage.valueOf(this.shotCoverage))
			.pictureRatio(PictureRatio.valueOf(this.pictureRatio))
			.facePictureKeyList(this.facePictureKeyList)
			.build();
	}

}
