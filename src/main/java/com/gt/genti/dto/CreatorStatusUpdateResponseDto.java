package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorStatusUpdateResponseDto {
	Boolean workable;

	@Builder
	public CreatorStatusUpdateResponseDto(Boolean workable) {
		this.workable = workable;
	}
}
