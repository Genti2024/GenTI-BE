package com.gt.genti.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonPictureResponseDto {
	Long id;
	String url;

	public CommonPictureResponseDto(Long id, String url) {
		this.id = id;
		this.url = url;
	}
}
