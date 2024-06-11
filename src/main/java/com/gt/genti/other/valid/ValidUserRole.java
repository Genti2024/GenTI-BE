package com.gt.genti.other.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ElementType.FIELD, ElementType.PARAMETER}) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = UserRoleValidator.class) // 3
public @interface ValidUserRole {

	String message() default "휴대폰 번호"; // 4

	Class[] groups() default {};

	Class[] payload() default {};
}
