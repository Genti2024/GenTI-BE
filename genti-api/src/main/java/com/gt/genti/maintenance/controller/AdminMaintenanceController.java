package com.gt.genti.maintenance.controller;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.maintenance.api.AdminMaintenanceApi;
import com.gt.genti.maintenance.service.MaintenanceService;
import com.gt.genti.response.GentiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/maintenance/setting")
@RequiredArgsConstructor
public class AdminMaintenanceController implements AdminMaintenanceApi {

    private final MaintenanceService maintenanceService;

    @Value("${openchat.admin-secret-key}")
    private String ADMIN_SECRET_KEY;

    @PostMapping
    public ResponseEntity<GentiResponse.ApiResult<Boolean>> setMaintenanceMessage(
            @RequestHeader(value = "Admin-Secret-Key") String adminSecretKey,
            @RequestBody String message
    ){
        if (!ADMIN_SECRET_KEY.equals(adminSecretKey)) {
            throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
        } else{
            return GentiResponse.success(maintenanceService.setMaintenanceMessage(message));
        }
    }

    @DeleteMapping
    public ResponseEntity<GentiResponse.ApiResult<Boolean>> finishMaintenance(
            @RequestHeader(value = "Admin-Secret-Key") String adminSecretKey
    ){
        if (!ADMIN_SECRET_KEY.equals(adminSecretKey)) {
            throw ExpectedException.withLogging(ResponseCode.InvalidOpenChatSecretKey);
        } else{
            return GentiResponse.success(maintenanceService.finishMaintenance());
        }
    }
}
