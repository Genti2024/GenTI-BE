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
public class KeyValidator extends MyStringValidator implements ConstraintValidator<ValidKey, String> {

	public static final String MESSAGE_TEMPLATE = "key 값은 FILE_TYPE 으로 시작해야합니다. 입력된 값 [%s]";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		for (FileType type : FileType.values()) {
			if (value.startsWith(type.getStringValue())) {
				return true;
			}
		}
		addConstraintViolation(constraintValidatorContext, MESSAGE_TEMPLATE, value);
		return false;
	}
}