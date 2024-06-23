package com.gt.genti.error;

import static com.gt.genti.error.ResponseCode.*;
import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpectedException.class)
	protected ResponseEntity<ApiResult<?>> handleExpectedException(
		final HttpServletRequest request,
		final ExpectedException exception) {
		if(exception.shouldLogError()){
			logError(request, exception.getResponseCode());
		}
		return error(exception.getResponseCode());
	}

	@ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
	public ResponseEntity<ApiResult<?>> handleNoHandlerFoundException(
		final HttpServletRequest request) {
		logError(request, NoHandlerFoundException);
		return error(NoHandlerFoundException);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResult<?>> handleHttpRequestMethodNotSupportedException(
		final HttpServletRequest request,
		final HttpRequestMethodNotSupportedException exception) {
		String arg1 = Arrays.toString(exception.getSupportedMethods());
		logError(request, MethodNotSupported, arg1);
		return error(MethodNotSupported, arg1);
	}

	@ExceptionHandler(WebExchangeBindException.class)
	protected ResponseEntity<ApiResult<?>> processValidationError(
		final HttpServletRequest request,
		final WebExchangeBindException exception) {
		String arg1 = exception.getBindingResult().getFieldErrors().stream().map(
			GlobalExceptionHandler::makeFieldErrorMessage).collect(Collectors.joining());
		logError(request, ControllerValidationError, arg1);
		return error(ControllerValidationError, arg1);
	}

	@ExceptionHandler(UnrecognizedPropertyException.class)
	protected ResponseEntity<ApiResult<?>> unRecognizedPropertyException(
		final HttpServletRequest request,
		UnrecognizedPropertyException exception) {
		String arg1 = exception.getMessage();
		logError(request, UnrecognizedPropertyException, arg1);
		return error(UnrecognizedPropertyException, arg1);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	protected ResponseEntity<ApiResult<?>> invalidDataAccessApiUsageException(
		final HttpServletRequest request,
		InvalidDataAccessApiUsageException exception) {
		String arg1 = exception.getMessage();
		logError(request, InvalidDataAccessApiUsageException, arg1);
		return error(InvalidDataAccessApiUsageException, arg1);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentNotValidException(
		final HttpServletRequest request,
		BindingResult bindingResult
	) {
		String arg1 = bindingResult
			.getFieldErrors()
			.stream()
			.map(GlobalExceptionHandler::makeFieldErrorMessage)
			.collect(Collectors.joining());
		logError(request, ControllerValidationError, arg1);
		return error(ControllerValidationError, arg1);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResult<?>> handleMethodArgumentTypeMismatchException(
		final HttpServletRequest request,
		MethodArgumentTypeMismatchException exception) {
		String arg1 = exception.getMessage();
		logError(request, MethodArgumentTypeMismatch, arg1);
		return error(MethodArgumentTypeMismatch, arg1);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<ApiResult<?>> handleMissingPathVariableException(
		final HttpServletRequest request,
		MissingPathVariableException exception) {
		String arg1 = exception.getMessage();
		logError(request, MissingPathVariableException, arg1);
		return error(MissingPathVariableException, arg1);
	}

	// Controller에서 @Min @NotNull 등의 기본적인 어노테이션 유효성 검사 오류시
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		final HttpServletRequest request,
		HandlerMethodValidationException exception) {
		MessageSourceResolvable resolvable = exception.getAllValidationResults().get(0).getResolvableErrors().get(0);
		String fieldName = Objects.requireNonNull(resolvable.getCodes())[0];
		fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
		String arg1 = fieldName + " 필드는 " + resolvable.getDefaultMessage();
		logError(request, ControllerValidationError, arg1);
		return error(ControllerValidationError, arg1);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResult<?>> handleValidationExceptions(
		final HttpServletRequest request,
		MissingServletRequestParameterException exception) {
		String arg1 = exception.getMessage();
		logError(request, ControllerValidationError, arg1);
		return error(ControllerValidationError, arg1);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResult<?>> handleHttpMessageNotReadableException(
		final HttpServletRequest request,
		HttpMessageNotReadableException exception) {
		String arg1 = exception.getMessage();
		logError(request, InValidFormat, arg1);
		return error(InValidFormat, arg1);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResult<?>> handleUnExpectedException(
		final HttpServletRequest request,
		Exception exception) {
		String arg1 = exception.getMessage();
		logError(request, UnHandledException, arg1);
		return error(UnHandledException, arg1);
	}



	@NotNull
	private static String makeFieldErrorMessage(FieldError fieldError) {
		return """
			[%s] 변수에 대해서 %s 입력된 값 : [%s]""".formatted(fieldError.getField(), fieldError.getDefaultMessage(),
			fieldError.getRejectedValue());
	}

	private void logError(HttpServletRequest request, ResponseCode responseCode, Object... args) {
		String errorMessage = responseCode.getMessage(args);
		String requestUrl = request.getRequestURL().toString();
		StringBuilder params = new StringBuilder();
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String paramValue = request.getParameter(paramName);
			params.append(paramName).append("=").append(paramValue).append(", ");
		}

		String user = "Anonymous"; // Default value
		if (request.getUserPrincipal() != null) {
			user = request.getUserPrincipal().getName();
		}

		// Log the error with all the collected information
		log.error("[{} error] occurred for user: {}, URL: {}, Params: [{}], Message: {}",
			responseCode.name(), user, requestUrl, params, errorMessage);
	}

}
