package com.gt.genti.dto.common.response;

import com.gt.genti.domain.enums.PictureRatio;
import com.gt.genti.external.aws.AwsUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureResponseDto {
	Long id;
	String url;
	String key;
	PictureRatio pictureRatio;
	String type;

	public CommonPictureResponseDto(Long id, String key, PictureRatio pictureRatio, String type) {
		this.id = id;
		this.key = key;
		this.url = AwsUtils.CLOUDFRONT_BASEURL + "/" + key;
		this.pictureRatio = pictureRatio;
		this.type = type;
	}
}
