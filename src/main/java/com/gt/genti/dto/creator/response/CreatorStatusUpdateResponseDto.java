package com.gt.genti.dto.creator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class CreatorStatusUpdateResponseDto {
	@Schema(name = "workable")
	Boolean workable;

	@Builder
	public CreatorStatusUpdateResponseDto(Boolean workable) {
		this.workable = workable;
	}
}
