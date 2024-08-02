package com.gt.genti.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Swager Authorization으로 토큰 넣으면 됩니다. 직접 작성하지마세요!! CREATOR, ADMIN의 AccessToken")
public @interface AuthorizedCreator {
}

