package com.gt.genti.error;

import static com.gt.genti.error.ResponseCode.*;
import static com.gt.genti.response.GentiResponse.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<?>> handleExpectedException(
		final ExpectedException exception) {
		if (exception.shouldLogError()) {
			log.error(exception.getMessage(), exception);
		}
		return error(exception.getResponseCode());
	}

	@ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
	public ResponseEntity<ApiResult<?>> handleNoHandlerFoundException(
		final HttpServletRequest request,
		final Exception exception) {
		log.error(exception.getMessage(), exception);
		return error(ResponseCode.HandlerNotFound);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResult<?>> handleHttpRequestMethodNotSupportedException(
		final HttpRequestMethodNotSupportedException exception) {
		String arg1 = Arrays.toString(exception.getSupportedMethods());
		log.error(exception.getMessage(), exception);
		return error(HttpRequestMethodNotSupported, arg1);
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	protected ResponseEntity<ApiResult<?>> unRecognizedPropertyException(
		UnrecognizedPropertyException exception) {
		String arg1 = exception.getMessage();
		log.error(exception.getMessage(), exception);
		return error(UnrecognizedProperty, arg1);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentNotValidException(
		BindingResult bindingResult,
		final MethodArgumentNotValidException exception
	) {
		String arg1 = bindingResult
			.getFieldErrors()
			.stream()
			.map(GlobalExceptionHandler::makeFieldErrorMessage)
			.collect(Collectors.joining());
		log.error(exception.getMessage(), exception);
		return error(HandlerMethodValidation, arg1);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		String arg1 = String.format("[%s]변수에 대해 잘못된 입력 : [%s], 변수의 형식은 [%s] 입니다", exception.getPropertyName(),
			exception.getValue(), exception.getRequiredType());
		log.error(exception.getMessage(), exception);
		return error(MethodArgumentTypeMismatch, arg1);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ApiResult<?>> handleMissingPathVariableException(
		final MissingPathVariableException exception) {
		log.error(exception.getMessage(), exception);
		return error(MissingPathVariable, exception.getMessage());
	}

	// Request Validation
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		HandlerMethodValidationException exception) {
		String arg1 = exception.getAllValidationResults().get(0).toString();
		log.error(exception.getMessage(), exception);
		return error(HandlerMethodValidation, arg1);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResult<?>> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException exception) {
		String arg1 = exception.getMessage();
		log.error(exception.getMessage(), exception);

		return error(HttpMessageNotReadable, arg1);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResult<?>> handleUnExpectedException(
		Exception exception) {
		String arg1 = exception.getMessage();
		log.error(exception.getMessage(), exception);
		return error(UnHandledException, arg1);
	}

	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			[%s] 변수에 대해서 %s 입력된 값 : [%s]""".formatted(fieldError.getField(), fieldError.getDefaultMessage(),
			fieldError.getRejectedValue());
	}
}
