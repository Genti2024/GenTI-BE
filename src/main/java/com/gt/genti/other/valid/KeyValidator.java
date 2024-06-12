package com.gt.genti.other.valid;

import com.gt.genti.dto.enums.FileType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyValidator implements ConstraintValidator<ValidKey, String> {

	public static final String MESSAGE_TEMPLATE = "key 값은 FILE_TYPE 으로 시작해야합니다.";
	public static final String KEY_VALIDATE = "key validate";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		for (FileType type : FileType.values()) {
			if (value.startsWith(type.getStringValue())) {
				return true;
			}
		}
		constraintValidatorContext.disableDefaultConstraintViolation();
		constraintValidatorContext
			.buildConstraintViolationWithTemplate(MESSAGE_TEMPLATE)
			.addPropertyNode(KEY_VALIDATE).addConstraintViolation();
		return false;
	}

}