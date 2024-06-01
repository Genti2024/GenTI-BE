package com.gt.genti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureUrlResponseDto {
	Long id;
	String url;

	public CommonPictureUrlResponseDto(Long id, String url) {
		this.id = id;
		this.url = url;
	}
}
