package com.gt.genti.picture.dto.response;

import com.gt.genti.aws.AwsUtils;
import com.gt.genti.common.picture.model.PictureEntity;
import com.gt.genti.picture.PictureRatio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Schema(name = "[Picture][Anonymous] 공통 사진 응답 dto")
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
		"PictureUserVerification",
		"PicturePost",
		"PictureProfile",
		"PictureUserFace",
		"ResponseExample"
	})
	String type;

	@Builder
	public CommonPictureResponseDto(Long id, String key, PictureRatio pictureRatio, String type) {
		this.id = id;
		this.key = key;
		this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + key;
		this.pictureRatio = pictureRatio;
		this.type = type;
	}

	public static CommonPictureResponseDto of(PictureEntity pictureEntity) {
		if (pictureEntity == null) {
			return null;
		}
		return CommonPictureResponseDto.builder()
			.id(pictureEntity.getId())
			.key(pictureEntity.getKey())
			.type(pictureEntity.getClass().getSimpleName())
			.pictureRatio(pictureEntity.getPictureRatio())
			.build();
	}

}
