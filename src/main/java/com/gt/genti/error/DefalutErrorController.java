package com.gt.genti.error;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DefalutErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	//Controller 이전의 예외를 잡기위함
	// @RequestMapping("/error")
	// public ResponseEntity<ApiResult<String>> handleError(WebRequest webRequest) {
	// 	Throwable throwable = errorAttributes.getError(webRequest);
	// 	Map<String, Object> errors = getErrorAttributes(webRequest);
	// 	StringBuilder errorDetails = new StringBuilder();
	// 	errorDetails.append("Timestamp: ").append(errors.get("timestamp")).append("\n");
	// 	errorDetails.append("Status: ").append(errors.get("status")).append("\n");
	// 	errorDetails.append("Error: ").append(errors.get("error")).append("\n");
	// 	errorDetails.append("Message: ").append(errors.get("message")).append("\n");
	// 	errorDetails.append("Path: ").append(errors.get("path")).append("\n");
	// 	if (errors.get("trace") != null) {
	// 		errorDetails.append("Trace: ").append(errors.get("trace")).append("\n");
	// 	}

		// return error(errorDetails.toString());
	// }

	private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
		return errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
	}

}
