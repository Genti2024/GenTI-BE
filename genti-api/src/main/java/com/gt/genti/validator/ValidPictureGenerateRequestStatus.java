// package com.gt.genti.other.valid;
//
// import java.lang.annotation.Documented;
// import java.lang.annotation.ElementType;
// import java.lang.annotation.Retention;
// import java.lang.annotation.RetentionPolicy;
// import java.lang.annotation.Target;
//
// import jakarta.validation.Constraint;
//
// @Documented
// @Target({ElementType.TYPE_USE, ElementType.FIELD}) // 1
// @Retention(RetentionPolicy.RUNTIME) // 2
// @Constraint(validatedBy = PictureGenerateRequestStatusValidator.class) // 3
// public @interface ValidPictureGenerateRequestStatus {
//
// 	String message() default "사진생성요청 상태"; // 4
//
// 	Class[] groups() default {};
//
// 	Class[] payload() default {};
// }
//
