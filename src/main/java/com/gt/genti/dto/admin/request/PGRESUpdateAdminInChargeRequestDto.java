package com.gt.genti.dto.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "사진생성응답 담당자 변경 Dto")

public class PGRESUpdateAdminInChargeRequestDto {
	@Schema(description = "담당자 이름", example = "안재욱")
	String adminInCharge;
}
