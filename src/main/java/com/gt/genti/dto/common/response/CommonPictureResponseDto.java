package com.gt.genti.dto.common.response;

import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.external.aws.AwsUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "공통 사진 응답 dto")
@Getter
@NoArgsConstructor
public class CommonPictureResponseDto {
	@Schema(description = "해당 사진의 Entity id", example = "1")
	Long id;

	@Schema(description = "해당 사진의 Url", example = "https://**")
	String url;

	@Schema(description = "해당 사진의 Key값", example = "USER_UPLOADED_IMAGE/**")
	String key;

	@Schema(description = "생성된 사진의 비율, 사진의 비율이 정해지지 않은 경우 \"NONE\"")
	PictureRatio pictureRatio;

	@Schema(description = "사진의 종류, 주로 문제 발생시 BE와 소통을 위해 전달", allowableValues = {
		"PictureCompleted",
		"PictureCreatedByCreator",
		"PicturePose",
		"PicturePost",
		"PictureProfile",
		"PictureUserFace",
		"ResponseExample"
	})
	String type;

	public CommonPictureResponseDto(Long id, String key, PictureRatio pictureRatio, String type) {
		this.id = id;
		this.key = key;
		this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + key;
		this.pictureRatio = pictureRatio;
		this.type = type;
	}
}
