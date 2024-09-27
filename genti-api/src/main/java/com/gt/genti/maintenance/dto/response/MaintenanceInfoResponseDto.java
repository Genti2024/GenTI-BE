package com.gt.genti.maintenance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "[Maintenance][User] 점검 정보 응답 dto")
@Getter
public class MaintenanceInfoResponseDto {

    @Schema(description = "서비스 이용 가능 여부", example = "false")
    Boolean status;

    @Schema(description = "점검 메시지", example = "09:00 ~ 18:00")
    String message;

    public MaintenanceInfoResponseDto(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

}
