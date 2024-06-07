package com.gt.genti.other.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyValidator implements ConstraintValidator<Key, String> {

	public static final String MESSAGE_TEMPLATE = "key 값은 /로 시작하는 문자열이여야합니다.";
	public static final String KEY_VALIDATE = "key validate";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value.startsWith("/")) {
			return true;
		}
		constraintValidatorContext.disableDefaultConstraintViolation();
		constraintValidatorContext
			.buildConstraintViolationWithTemplate(MESSAGE_TEMPLATE)
			.addPropertyNode(KEY_VALIDATE).addConstraintViolation();
		return false;
	}

}