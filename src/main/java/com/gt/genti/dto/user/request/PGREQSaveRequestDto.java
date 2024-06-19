package com.gt.genti.dto.user.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.enums.CameraAngle;
import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.domain.enums.ShotCoverage;
import com.gt.genti.other.valid.ValidEnum;
import com.gt.genti.other.valid.ValidKey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PGREQSaveRequestDto {

	@NotBlank
	@JsonProperty("prompt")
	String prompt;
	@ValidKey @NotNull @JsonProperty("posePictureKey")
	String posePictureKey;

	@NotNull
	@Size(max = 3, min = 1, message = "사용자의 얼굴 사진 개수는 최소 1개, 최대 3개입니다.")
	@JsonProperty("facePictureKeyList")
	List<@ValidKey String> facePictureKeyList;

	@NotNull
	@ValidEnum(value = CameraAngle.class)
	@JsonProperty("cameraAngle")
	String cameraAngle;
	@NotNull
	@ValidEnum(value = ShotCoverage.class)
	@JsonProperty("shotCoverage")
	String shotCoverage;
	@NotNull
	@ValidEnum(value = PictureRatio.class)
	@JsonProperty("pictureRatio")
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
