package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class DefalutErrorController implements ErrorController {

	// Controller 이전의 예외를 잡기위함
	@RequestMapping("/error")
	public ResponseEntity<ApiResult<ExpectedException>> handleError(HttpServletRequest request,
		HttpServletResponse response) {
		String requestURI = request.getRequestURI();

		return error(ExpectedException.withLogging(DefaultErrorCode.NoHandlerFoundException, requestURI));
	}

}
