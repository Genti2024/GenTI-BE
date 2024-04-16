package com.gt.genti.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.gt.genti.security.PrincipalDetail;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class CheckUserIsQuitAop {
	@Before("@annotation(com.gt.genti.aop.CheckUserIsQuit) && args(principalDetail)")
	public void calculateExecutionTime(final PrincipalDetail principalDetail) {
		log.info("유저탈퇴확인aop실행");
		if (!principalDetail.isEnabled()) {
			throw new RuntimeException("탈퇴한 사용자입니다.");
		}
	}
}
