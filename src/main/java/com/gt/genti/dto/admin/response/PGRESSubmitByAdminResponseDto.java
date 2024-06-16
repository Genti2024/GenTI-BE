package com.gt.genti.dto.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGRESSubmitByAdminResponseDto {
	Long id;
	String elapsedTime;

	@Builder
	public PGRESSubmitByAdminResponseDto(Long id, String elapsedTime) {
		this.id = id;
		this.elapsedTime = elapsedTime;
	}
}
