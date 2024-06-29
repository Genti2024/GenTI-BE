package com.gt.genti.validator;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DefaultEnumValidator extends BaseValidator implements ConstraintValidator<ValidEnum, String> {
	private Class<? extends Enum<?>> enumClass;
	private boolean hasAllOption;

	@Override
	public void initialize(ValidEnum constraintAnnotation) {
		this.enumClass = constraintAnnotation.value();
		this.hasAllOption = constraintAnnotation.hasAllOption();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true; // Use @NotNull for null checks
		}
		if (hasAllOption && "ALL".equalsIgnoreCase(value)) {
			return true;
		}
		if (Arrays.stream(enumClass.getEnumConstants())
			.anyMatch(e -> e.name().equals(value))) {
			return true;
		}
		addConstraintViolation(context);
		return false;
	}

}