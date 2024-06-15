package com.gt.genti.adapter.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.gt.genti.other.util.ApiUtils;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController {

	private final ErrorAttributes errorAttributes;

	@Autowired
	public CustomErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@GetMapping("/error")
	public ResponseEntity<ApiUtils.ApiResult<String>> error(HttpServletRequest request) {
		WebRequest webRequest = new ServletWebRequest(request);

		Throwable error = errorAttributes.getError(webRequest);

		// ApiUtils.error() 메서드에 에러 메시지를 전달합니다.
		return ApiUtils.error(error.getMessage());
	}
}
