package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> handleExpectedException(final ExpectedException exception) {
		return error(exception);
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
		log.info(exception.getMessage());

		// return error(new DynamicException("VALIDATION", errorMessage, HttpStatus.BAD_REQUEST));
		return null;
	}

	// Controller에서 @Min @NotNull 등의 어노테이션 유효성 검사 오류시
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResult<ExpectedException>> handleValidationExceptions(
		HandlerMethodValidationException exception) {
		MessageSourceResolvable resolvable = exception.getAllValidationResults().get(0).getResolvableErrors().get(0);
		String fieldName = Objects.requireNonNull(resolvable.getCodes())[0];
		fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
		String errorMessage = resolvable.getDefaultMessage();
		String error = fieldName + " 필드는 " + errorMessage;

		return error(new ExpectedException(ErrorCode.ValidationError, error));
	}

	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			%s 은(는) %s
			입력된 값 : %s
			""".formatted(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
	}
}
