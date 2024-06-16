package com.gt.genti.dto.admin.response;

import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PGRESUpdateAdminInChargeResponseDto {
	Long id;
	PictureGenerateResponseStatus status;
	String adminInCharge;

	@Builder
	public PGRESUpdateAdminInChargeResponseDto(Long id, PictureGenerateResponseStatus status, String adminInCharge) {
		this.id = id;
		this.status = status;
		this.adminInCharge = adminInCharge;
	}
}
