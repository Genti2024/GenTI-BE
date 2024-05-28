package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<?>> handleExpectedException(final ExpectedException exception) {
		return error(exception.getErrorCode());
	}

	// @ExceptionHandler(RuntimeException.class)
	// protected ResponseEntity<ApiResult<?>> handleUnExpectedException(final RuntimeException exception) {
	// 	String error = """
	// 		Class : %s
	// 		Cause : %s
	// 		Message : %s
	// 		StackTrace : %s
	// 		""".formatted(exception.getClass(), exception.getCause(), exception.getMessage(),
	// 		exception.getStackTrace());
	// 	log.error(error);
	// 	return error(ErrorCode.UnHandledException);
	// }

	@ExceptionHandler(WebExchangeBindException.class)
	protected ResponseEntity<ApiResult<?>> processValidationError(WebExchangeBindException exception) {
		String errorMessage = exception.getBindingResult().getFieldErrors().stream().map(
			GlobalExceptionHandler::makeFieldErrorMessage).collect(Collectors.joining());

		return error(new DynamicException("VALIDATION", errorMessage, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(DynamicException.class)
	protected ResponseEntity<ApiResult<?>> processValidationError(DynamicException exception) {
		String errorMessage = exception.getMessage();

		return error(new DynamicException(exception.getErrorCode(), errorMessage, exception.getHttpStatusCode()));
	}

	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			%s 은(는) %s
			입력된 값 : %s
			""".formatted(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
	}
}
