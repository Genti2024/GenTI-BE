package com.gt.genti.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;

@Documented
@Target({ElementType.TYPE_USE, ElementType.FIELD}) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = KeyValidator.class) // 3
public @interface ValidKey {
	String message() default "S3 KEY 형식"; // 4

	Class[] groups() default {};

	Class[] payload() default {};
}
