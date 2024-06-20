package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<?>> handleExpectedException(final HttpServletRequest request,
		final ExpectedException exception) {

		return error(exception);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiResult<?>> handleNoHandlerFoundException(
		NoHandlerFoundException ex) {
		String errorMessage = ex.getRequestURL();
		return error(ExpectedException.withLogging(DefaultErrorCode.NoHandlerFoundException, errorMessage));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResult<?>> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		return error(ExpectedException.withLogging(DefaultErrorCode.MethodNotSupported, ex.getMessage(),
			Arrays.toString(ex.getSupportedMethods())));
	}

	//없는 url로 요청 시
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResult<?>> handleNoResourceFoundException(
		NoResourceFoundException ex) {
		String errorMessage = ex.getResourcePath();
		return error(ExpectedException.withLogging(DefaultErrorCode.NoHandlerFoundException, errorMessage));
	}

	@ExceptionHandler(WebExchangeBindException.class)
	protected ResponseEntity<ApiResult<?>> processValidationError(WebExchangeBindException exception) {
		String errorMessage = exception.getBindingResult().getFieldErrors().stream().map(
			GlobalExceptionHandler::makeFieldErrorMessage).collect(Collectors.joining());
		return error(ExpectedException.withLogging(DefaultErrorCode.ControllerValidationError, errorMessage));
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	protected ResponseEntity<ApiResult<?>> unRecognizedPropertyException(
		UnrecognizedPropertyException exception) {
		String errorMessage = exception.getMessage();
		return error(ExpectedException.withLogging(DefaultErrorCode.UnrecognizedPropertyException, errorMessage));
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	protected ResponseEntity<ApiResult<?>> invalidDataAccessApiUsageException(
		InvalidDataAccessApiUsageException exception) {
		String errorMessage = exception.getMessage();

		return error(
			ExpectedException.withLogging(DefaultErrorCode.InvalidDataAccessApiUsageException, errorMessage));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException exception) {
		exception.getMessage();

		String error = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(GlobalExceptionHandler::makeFieldErrorMessage)
			.collect(Collectors.joining());
		return error(ExpectedException.withLogging(DefaultErrorCode.ControllerValidationError, error));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {

		return error(ExpectedException.withLogging(DefaultErrorCode.MethodArgumentTypeMismatch, ex.getMessage()));
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ApiResult<?>> handleMissingPathVariableException(
		MissingPathVariableException ex) {

		return error(ExpectedException.withLogging(DefaultErrorCode.MissingPathVariableException, ex.getMessage()));
	}

	// Controller에서 @Min @NotNull 등의 기본적인 어노테이션 유효성 검사 오류시

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		HandlerMethodValidationException exception) {
		MessageSourceResolvable resolvable = exception.getAllValidationResults().get(0).getResolvableErrors().get(0);
		String fieldName = Objects.requireNonNull(resolvable.getCodes())[0];
		fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
		String errorMessage = resolvable.getDefaultMessage();
		String error = fieldName + " 필드는 " + errorMessage;

		return error(ExpectedException.withLogging(DefaultErrorCode.ControllerValidationError, error));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		MissingServletRequestParameterException exception) {
		return error(ExpectedException.withLogging(DefaultErrorCode.ControllerValidationError, exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResult<?>> handleUnExpectedException(
		Exception exception) {
		String msg = exception.getMessage();
		return error(ExpectedException.withLogging(DefaultErrorCode.UnHandledException, msg));
	}


	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			[%s] 변수에 대해서 %s 입력된 값 : [%s]  """.formatted(fieldError.getField(), fieldError.getDefaultMessage(),
			fieldError.getRejectedValue());

	}

}
