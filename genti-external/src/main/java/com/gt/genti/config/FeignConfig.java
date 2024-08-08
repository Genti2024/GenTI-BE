package com.gt.genti.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.gt.genti.ExternalRoot;

@Configuration
@EnableFeignClients(basePackageClasses = ExternalRoot.class)
public class FeignConfig {
}

