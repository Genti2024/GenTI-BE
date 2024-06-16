package com.gt.genti.other.valid;

import com.gt.genti.domain.enums.WithdrawRequestStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WithdrawStatusValidator extends MyStringValidator implements ConstraintValidator<ValidWithdrawStatus, String> {
	public static final String MESSAGE_TEMPLATE = "적절하지 않은 입력, 입력된 값 : [%s]";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			addConstraintViolation(constraintValidatorContext, MESSAGE_TEMPLATE, "");
		}
		if ("ALL".equals(value)) {
			return true;
		}
		try {
			WithdrawRequestStatus.valueOf(value);
		} catch (IllegalArgumentException e) {
			addConstraintViolation(constraintValidatorContext, MESSAGE_TEMPLATE, value);
			return false;
		}

		return true;
	}
}
