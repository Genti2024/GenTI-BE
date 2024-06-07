package com.gt.genti.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGRESUpdateByAdminResponseDto {
	String elapsedTime;

	@Builder
	public PGRESUpdateByAdminResponseDto(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
