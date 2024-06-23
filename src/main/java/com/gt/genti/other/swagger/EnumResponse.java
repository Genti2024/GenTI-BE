package com.gt.genti.other.swagger;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gt.genti.error.ResponseCode;

@Target({ANNOTATION_TYPE, METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumResponse {
	ResponseCode value();
	boolean useReturnTypeSchema() default true;
}
