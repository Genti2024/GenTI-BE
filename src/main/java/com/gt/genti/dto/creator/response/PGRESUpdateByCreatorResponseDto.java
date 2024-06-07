package com.gt.genti.dto.creator.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGRESUpdateByCreatorResponseDto {
	String elapsedTime;
	Long reward;

	@Builder
	public PGRESUpdateByCreatorResponseDto(String elapsedTime, Long reward) {
		this.elapsedTime = elapsedTime;
		this.reward = reward;
	}
}
