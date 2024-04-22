package com.gt.genti.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PictureGenerateRequestResponseDto {

	String message;

	@Builder
	public PictureGenerateRequestResponseDto(String message) {
		this.message = message;
	}
}
