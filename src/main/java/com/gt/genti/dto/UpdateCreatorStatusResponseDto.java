package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCreatorStatusResponseDto {
	Boolean workable;

	@Builder
	public UpdateCreatorStatusResponseDto(Boolean workable) {
		this.workable = workable;
	}
}
