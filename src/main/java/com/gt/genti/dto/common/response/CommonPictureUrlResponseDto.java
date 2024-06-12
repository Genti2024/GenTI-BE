package com.gt.genti.dto.common.response;

import com.gt.genti.external.aws.AwsUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureUrlResponseDto {
	Long id;
	String url;
	String key;

	public CommonPictureUrlResponseDto(Long id, String key) {
		this.id = id;
		this.key = key;
		this.url = AwsUtils.CLOUDFRONT_BASEURL +"/" +key;
	}
}
