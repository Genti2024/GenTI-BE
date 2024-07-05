// package com.gt.genti.other.valid;
//
// import com.gt.genti.dto.enums.FileType;
//
// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;
//
// public class PictureGenerateRequestStatusValidator implements ConstraintValidator<ValidPictureGenerateRequestStatus, String> {
// 	@Override
// 	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
// 		if (value == null)
// 			return true;
// 		for ( type : FileType.values()) {
// 			if (value.startsWith(type.getStringValue())) {
// 				return true;
// 			}
// 		}
// 		addConstraintViolation(constraintValidatorContext);
// 		return false;
// 	}
// }
