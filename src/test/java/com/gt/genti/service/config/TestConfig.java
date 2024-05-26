package com.gt.genti.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.gt.genti.other.config.ScheduleConfig;

@Configuration
@ComponentScan(basePackages = "com.gt.genti", excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ScheduleConfig.class})
})
public class TestConfig {
	// 추가 테스트 설정
}