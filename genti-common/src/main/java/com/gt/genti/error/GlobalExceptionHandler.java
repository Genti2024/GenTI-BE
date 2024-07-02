package com.gt.genti.error;

import static com.gt.genti.error.ResponseCode.*;
import static com.gt.genti.response.GentiResponse.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
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
		final HttpServletRequest request,
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
		if (!request.getRequestURI().endsWith("favicon.ico")) {
			log.error(exception.getMessage(), exception);
		}
		return error(NoHandlerFoundException);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResult<?>> handleHttpRequestMethodNotSupportedException(
		final HttpRequestMethodNotSupportedException exception) {
		String arg1 = Arrays.toString(exception.getSupportedMethods());
		log.error(exception.getMessage(), exception);
		return error(HttpRequestMethodNotSupportedException, arg1);
	}

	// @ExceptionHandler(WebExchangeBindException.class)
	// protected ResponseEntity<ApiResult<?>> processValidationError(
	// 	final HttpServletRequest request,
	// 	final WebExchangeBindException exception) {
	// 	String arg1 = exception.getBindingResult().getFieldErrors().stream().map(
	// 		GlobalExceptionHandler::makeFieldErrorMessage).collect(Collectors.joining());
	// 	logError(request, HandlerMethodValidationException, arg1);
	// 	return error(HandlerMethodValidationException, arg1);
	// }

	@ExceptionHandler(UnrecognizedPropertyException.class)
	protected ResponseEntity<ApiResult<?>> unRecognizedPropertyException(
		UnrecognizedPropertyException exception) {
		String arg1 = exception.getMessage();
		log.error(exception.getMessage(), exception);
		return error(UnrecognizedPropertyException, arg1);
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
		return error(HandlerMethodValidationException, arg1);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		String arg1 = String.format("[%s]변수에 대해 잘못된 입력 : [%s], 변수의 형식은 [%s] 입니다", exception.getPropertyName(),
			exception.getValue(), exception.getRequiredType());
		log.error(exception.getMessage(), exception);
		return error(MethodArgumentTypeMismatchException, arg1);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ApiResult<?>> handleMissingPathVariableException(
		final MissingPathVariableException exception) {
		log.error(exception.getMessage(), exception);
		return error(MissingPathVariableException, exception.getMessage());
	}

	// Controller에서 @Min @NotNull 등의 기본적인 어노테이션 유효성 검사 오류시
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		HandlerMethodValidationException exception) {
		MessageSourceResolvable resolvable = exception.getAllValidationResults().get(0).getResolvableErrors().get(0);
		String fieldName = Objects.requireNonNull(resolvable.getCodes())[0];
		fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
		String arg1 = fieldName + " 필드는 " + resolvable.getDefaultMessage();
		log.error(exception.getMessage(), exception);
		return error(HandlerMethodValidationException, arg1);
	}

	// @ExceptionHandler(MissingServletRequestParameterException.class)
	// public ResponseEntity<ApiResult<?>> handleValidationExceptions(
	// 	final HttpServletRequest request,
	// 	MissingServletRequestParameterException exception) {
	// 	String arg1 = exception.getMessage();
	// 	logError(request, ControllerValidationError, arg1);
	// 	return error(ControllerValidationError, arg1);
	// }

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResult<?>> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException exception) {
		String arg1 = exception.getMessage();
		log.error(exception.getMessage(), exception);

		return error(HttpMessageNotReadableException, arg1);
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
