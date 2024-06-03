package com.gt.genti.other.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("within(@org.springframework.stereotype.Service *) || within(@org.springframework.stereotype.Component *) || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// 포인트컷
	}

	@Pointcut("within(com.gt.genti..*)")
	public void applicationPackagePointcut() {
		// 포인트컷
	}

	@Before("springBeanPointcut() && applicationPackagePointcut()")
	public void logBefore(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String className = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		log.info("Executing method: {}.{}", className, methodName);
	}

	@AfterReturning(pointcut = "springBeanPointcut() && applicationPackagePointcut()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String className = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		log.info("Method {}.{} executed successfully, returning: {}", className, methodName, result);
	}

	@AfterThrowing(pointcut = "springBeanPointcut() && applicationPackagePointcut()", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String className = signature.getDeclaringTypeName();
		String methodName = signature.getName();
		log.error("Method {}.{} threw an exception: {}", className, methodName, error.getMessage());
	}
}
