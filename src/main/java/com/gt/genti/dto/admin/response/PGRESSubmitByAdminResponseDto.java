package com.gt.genti.dto.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class PGRESSubmitByAdminResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "elapsedTime")
	String elapsedTime;

	@Builder
	public PGRESSubmitByAdminResponseDto(Long id, String elapsedTime) {
		this.id = id;
		this.elapsedTime = elapsedTime;
	}
}
