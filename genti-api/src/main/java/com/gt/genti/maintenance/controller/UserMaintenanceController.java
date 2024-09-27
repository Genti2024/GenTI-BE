package com.gt.genti.maintenance.controller;

import com.gt.genti.maintenance.api.UserMaintenanceApi;
import com.gt.genti.maintenance.dto.response.MaintenanceInfoResponseDto;
import com.gt.genti.maintenance.service.MaintenanceService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/maintenance")
@RequiredArgsConstructor
public class UserMaintenanceController implements UserMaintenanceApi {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<ApiResult<MaintenanceInfoResponseDto>> getMaintenanceInfo() {
        return GentiResponse.success(maintenanceService.getMaintenanceInfo());
    }
}
