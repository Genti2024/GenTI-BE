package com.gt.genti.validator;

import com.gt.genti.aws.FileType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class KeyValidator extends BaseValidator implements ConstraintValidator<ValidKey, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null)
			return true;
		for (FileType type : FileType.values()) {
			if (value.startsWith(type.getStringValue())) {
				return true;
			}
		}
		addConstraintViolation(constraintValidatorContext);
		return false;
	}
}