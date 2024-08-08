package com.gt.genti.picturegenerateresponse.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[PGRES][Admin] 사진생성응답 담당 어드민 변경 Dto")
public class PGRESUpdateAdminInChargeRequestDto {
	@Schema(description = "담당자를 설정하고자 하는 사진생성응답 DB ID", example = "1")
	Long pictureGenerateResponseId;
	@Schema(description = "담당자 이름", example = "안재욱")
	String adminInCharge;
}
