package com.gt.genti.other.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class EnvirenmentAspect {

	private final Environment env;

	@Around("@annotation(com.gt.genti.other.aop.annotation.DeployOnly)")
	public Object deployOnly(ProceedingJoinPoint joinPoint) throws Throwable {
		String[] activeProfiles = env.getActiveProfiles();
		for (String profile : activeProfiles) {
			if ("deploy".equals(profile)) {
				return joinPoint.proceed();
			}
		}
		return null;
	}
}
