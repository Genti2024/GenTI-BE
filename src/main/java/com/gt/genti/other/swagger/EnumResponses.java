package com.gt.genti.other.swagger;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({METHOD, TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnumResponses {
	EnumResponse[] value() default {};

	EnumResponseGroup[] groups() default {};
}


