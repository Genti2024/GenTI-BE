package com.gt.genti.maintenance.api;

import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.AuthorizedAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@AuthorizedAdmin
@Tag(name = "[AdminMaintenanceController] 점검 설정", description = "점검 시작 및 점검 종료하기")
public interface AdminMaintenanceApi {

    @Operation(summary = "점검 시작 메시지 설정", description = "점검 시작 메시지를 설정합니다(해당 api 호출 즉시 점검이 시작됩니다")
    ResponseEntity<GentiResponse.ApiResult<Boolean>> setMaintenanceMessage(
            @RequestHeader(value = "Admin-Secret-Key") String secretKey,
            @RequestBody String type
    );

    @Operation(summary = "점검 종료", description = "점검을 종료합니다")
    ResponseEntity<GentiResponse.ApiResult<Boolean>> finishMaintenance(
            @RequestHeader(value = "Admin-Secret-Key") String secretKey
    );

}
