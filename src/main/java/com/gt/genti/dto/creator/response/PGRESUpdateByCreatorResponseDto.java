package com.gt.genti.dto.creator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class PGRESUpdateByCreatorResponseDto {
	@Schema(name = "elapsedTime")
	String elapsedTime;
	@Schema(name = "reward")
	Long reward;

	@Builder
	public PGRESUpdateByCreatorResponseDto(String elapsedTime, Long reward) {
		this.elapsedTime = elapsedTime;
		this.reward = reward;
	}
}
