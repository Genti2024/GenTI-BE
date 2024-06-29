package com.gt.genti.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireImageUpload {
	String description() default "[선행작업 필요] 사진 업로드 수행 한 후 Key를 업로드합니다.";
}
