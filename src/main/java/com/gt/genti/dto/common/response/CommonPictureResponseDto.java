package com.gt.genti.dto.common.response;

import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.external.aws.AwsUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class CommonPictureResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "url")
	String url;
	@Schema(name = "key")
	String key;
	@Schema(name = "pictureRatio")
	PictureRatio pictureRatio;
	@Schema(name = "type")
	String type;

	public CommonPictureResponseDto(Long id, String key, PictureRatio pictureRatio, String type) {
		this.id = id;
		this.key = key;
		this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + key;
		this.pictureRatio = pictureRatio;
		this.type = type;
	}
}
