package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureGenerateResponseAdminSubmitDto {
	String elapsedTime;

	@Builder
	public PictureGenerateResponseAdminSubmitDto(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
