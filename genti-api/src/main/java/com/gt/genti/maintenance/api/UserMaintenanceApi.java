package com.gt.genti.maintenance.api;

import com.gt.genti.maintenance.dto.response.MaintenanceInfoResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@AuthorizedUser
@Tag(name = "[UserMaintenanceController] 점검 정보 확인", description = "서비스 이용 가능 여부 및 점검 메시지 확인")
public interface UserMaintenanceApi {

    @Operation(summary = "점검 정보 확인", description = "서비스 이용 가능 여부 및 점검 메시지를 조회합니다.")
    ResponseEntity<GentiResponse.ApiResult<MaintenanceInfoResponseDto>> getMaintenanceInfo();

}
