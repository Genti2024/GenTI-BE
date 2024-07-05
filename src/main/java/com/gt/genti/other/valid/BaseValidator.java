package com.gt.genti.other.valid;

import jakarta.validation.ConstraintValidatorContext;

public class BaseValidator {
	protected void addConstraintViolation(ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		// 검증 실패한 항목들에 대해 모두 violation을 추가할 수 있으며 이를 exception handler에서 처리가 가능하다.
		context.buildConstraintViolationWithTemplate("잘못된 입력")
			.addConstraintViolation();
	}
}
