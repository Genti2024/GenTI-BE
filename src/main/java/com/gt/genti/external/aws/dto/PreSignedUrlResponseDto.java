package com.gt.genti.external.aws.dto;

import java.net.URL;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreSignedUrlResponseDto {
	URL url;

	@Builder

	public PreSignedUrlResponseDto(URL url) {
		this.url = url;
	}
}
