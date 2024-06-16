package com.gt.genti.other.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ElementType.FIELD, ElementType.PARAMETER}) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = WithdrawStatusValidator.class) // 3
public @interface ValidWithdrawStatus {

	String message() default "출금요청 상태"; // 4

	Class[] groups() default {};

	Class[] payload() default {};
}

