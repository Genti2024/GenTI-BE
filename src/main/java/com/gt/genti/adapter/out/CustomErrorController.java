package com.gt.genti.adapter.out;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.other.util.ApiUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomErrorController {

	// @GetMapping("/error")
	// public ResponseEntity<ApiUtils.ApiResult<String>> error(HttpServletRequest request) {
	// 	return ApiUtils.error("""
	// 		오류 path : [%s] """.formatted(request.getRequestURI()));
	// }
}
