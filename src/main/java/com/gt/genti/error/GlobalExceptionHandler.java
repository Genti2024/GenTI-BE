package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> handleExpectedException(final HttpServletRequest request,
		final ExpectedException exception) {
		log.error("""
			\n[Error] uri : %s \n%s""".formatted(request.getRequestURI(), exception.toString()));
		return error(exception);
	}

	@ExceptionHandler({MissingFormatArgumentException.class, NoHandlerFoundException.class,
		NoResourceFoundException.class})
	public ResponseEntity<ApiResult<ExpectedException>> handleNoHandlerFoundException(
		MissingFormatArgumentException ex) {
		return error(new ExpectedException(DefaultErrorCode.NoHandlerFoundException));
	}

	@ExceptionHandler(WebExchangeBindException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> processValidationError(WebExchangeBindException exception) {
		String errorMessage = exception.getBindingResult().getFieldErrors().stream().map(
			GlobalExceptionHandler::makeFieldErrorMessage).collect(Collectors.joining());
		return error(new ExpectedException(DefaultErrorCode.ValidationError, errorMessage));
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> unRecognizedPropertyException(UnrecognizedPropertyException exception) {
		String errorMessage = exception.getMessage();
		return error(new ExpectedException(DefaultErrorCode.UnrecognizedPropertyException, errorMessage));
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception){
		String errorMessage = exception.getMessage();

		return error(new ExpectedException(DefaultErrorCode.InvalidDataAccessApiUsageException, errorMessage));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult<ExpectedException>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		exception.getMessage();
		String error = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(GlobalExceptionHandler::formatError)
			.collect(Collectors.joining());
		return error(new ExpectedException(DefaultErrorCode.ValidationError, error));
	}


	// @ExceptionHandler(HttpMessageNotReadableException.class)
	// public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	// 	return new ResponseEntity<>("Malformed JSON request", HttpStatus.BAD_REQUEST);
	// }
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		return new ResponseEntity<>("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
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

		return error(new ExpectedException(DefaultErrorCode.ValidationError, error));
	}
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ApiResult<ExpectedException>> handleUnExpectedException(final RuntimeException exception) {
		String error = """
			Class : %s
			Cause : %s
			Message : %s
			StackTrace : %s
			""".formatted(exception.getClass(), exception.getCause(), exception.getMessage(),
			exception.getStackTrace());
		log.error(error);
		return error(new ExpectedException(DefaultErrorCode.UnHandledException, error));
	}

	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			%s 은(는) %s
			입력된 값 : %s
			""".formatted(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
	}

	private static String formatError(FieldError fieldError) {
		return """
			[%s]는 %s 입력된 값 : [%s]""".formatted(fieldError.getField(), fieldError.getDefaultMessage(),
			fieldError.getRejectedValue());
	}
}
