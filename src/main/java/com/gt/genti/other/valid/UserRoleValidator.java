package com.gt.genti.other.valid;

import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.converter.db.EnumUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserRoleValidator implements ConstraintValidator<ValidUserRole, String> {

	public static final String MESSAGE_TEMPLATE = "적절하지 않은 입력, 입력된 값 : [%s]";
	public static final String PROPERTY_NODE = "[UserRole]";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (EnumUtil.validateInputString(UserRole.class, value) || "ALL".equals(value)) {
			return true;
		} else {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext
				.buildConstraintViolationWithTemplate(String.format(MESSAGE_TEMPLATE, value))
				.addPropertyNode(PROPERTY_NODE).addConstraintViolation();
			return false;
		}
	}

}