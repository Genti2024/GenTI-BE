package com.gt.genti.dto.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class PGRESUpdateAdminInChargeRequestDto {
	@Schema(name = "adminInCharge")
	String adminInCharge;
}
