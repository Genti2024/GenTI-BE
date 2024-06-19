package com.gt.genti.other.valid;

import com.gt.genti.dto.enums.FileType;

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
		for (FileType type : FileType.values()) {
			if (value.startsWith(type.getStringValue())) {
				return true;
			}
		}
		addConstraintViolation(constraintValidatorContext);
		return false;
	}
}