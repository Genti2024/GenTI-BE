package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PictureGenerateResponseSubmitDto {
	String elapsedTime;
	Long reward;

	@Builder
	public PictureGenerateResponseSubmitDto(String elapsedTime, Long reward) {
		this.elapsedTime = elapsedTime;
		this.reward = reward;
	}
}
