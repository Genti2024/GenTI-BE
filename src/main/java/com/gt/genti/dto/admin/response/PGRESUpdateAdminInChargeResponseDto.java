package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor
public class PGRESUpdateAdminInChargeResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "status")
	PictureGenerateResponseStatus status;
	@Schema(name = "adminInCharge")
	String adminInCharge;

	@Builder
	public PGRESUpdateAdminInChargeResponseDto(Long id, PictureGenerateResponseStatus status, String adminInCharge) {
		this.id = id;
		this.status = status;
		this.adminInCharge = adminInCharge;
	}
}
