package com.gt.genti.other.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = KeyValidator.class) // 3
public @interface ValidKey {

	String message() default "S3 KEY 형식"; // 4

	Class[] groups() default {};

	Class[] payload() default {};
}
