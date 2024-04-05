package com.gt.genti.error;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<String> handleExpectedException(final ExpectedException exception) {
		return ResponseEntity.status(exception.getStatus())
			.body(exception.getErrorCode().getCode() + exception.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<String> handleUnExpectedException(final RuntimeException exception) {
		System.out.println(exception.getClass() + " exception occurred");
		System.out.println("Cause : " + exception.getCause());
		System.out.println("Message : " + exception.getMessage());
		Arrays.stream(exception.getStackTrace()).forEach(System.out::println);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예기치 못한 문제가 발생했습니다.");
	}

	@ExceptionHandler(WebExchangeBindException.class)
	protected ResponseEntity<String> processValidationError(WebExchangeBindException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
	}
}
