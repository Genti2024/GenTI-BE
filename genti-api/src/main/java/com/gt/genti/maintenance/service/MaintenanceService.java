package com.gt.genti.maintenance.service;

import com.gt.genti.maintenance.dto.response.MaintenanceInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MaintenanceService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("{maintenance.redis-key}")
    private String maintenanceKey;

    public MaintenanceInfoResponseDto getMaintenanceInfo() {
        String maintenanceInfo = redisTemplate.opsForValue().get(maintenanceKey);
        if (maintenanceInfo == null) {
            return new MaintenanceInfoResponseDto(true, null);
        } else {
             return new MaintenanceInfoResponseDto(false, maintenanceInfo);
        }
    }

    public Boolean setMaintenanceMessage(String message){
        redisTemplate.opsForValue().set(maintenanceKey, message);
        return true;
    }

    public Boolean finishMaintenance(){
        return redisTemplate.delete(maintenanceKey);
    }

}
