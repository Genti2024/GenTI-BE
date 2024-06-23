package com.gt.genti.dto.user.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "ㅁㄴㅇㄹ")

public class PGREQUpdateRequestDto {

	@Schema(name = "pictureGenerateRequestId")
	Long pictureGenerateRequestId;
	@Schema(name = "prompt")
	String prompt;
	@Schema(name = "posePictureUrl")
	String posePictureUrl;
	@Schema(name = "facePictureUrlList")
	List<String> facePictureUrlList;
	@Schema(name = "cameraAngle")
	String cameraAngle;
	@Schema(name = "shotCoverage")
	String shotCoverage;

	@Builder
	public PGREQUpdateRequestDto(Long id, String prompt, String posePictureUrl, String cameraAngle,
		String shotCoverage) {
		this.pictureGenerateRequestId = id;
		this.prompt = prompt;
		this.posePictureUrl = posePictureUrl;
		this.cameraAngle = cameraAngle;
		this.shotCoverage = shotCoverage;
	}
}

