package com.gt.genti.other.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.gt.genti.other.config.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class EnvAop {

	private final Environment env;

	@Before("@annotation(com.gt.genti.other.aop.annotation.CheckUserIsQuit) && args(userDetailsImpl)")
	public void checkUserIsQuit(final UserDetailsImpl userDetailsImpl) {
		log.info("유저탈퇴확인aop실행");
		if (!userDetailsImpl.isEnabled()) {
			throw new RuntimeException("탈퇴한 사용자입니다.");
		}
	}

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
