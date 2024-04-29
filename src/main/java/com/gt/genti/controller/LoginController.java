package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.fortest.TestJwtResponseDto;
import com.gt.genti.security.JwtTokenProvider;
import com.gt.genti.util.ApiUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/oauth2")
	public String index() {
		return "oauth2";
	}

	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TestJwtResponseDto>> getTestJwt() {
		Map<String, Object> tempClaim = Map.of("auth", "ROLE_USER", "sub", "3");
		String accessToken = jwtTokenProvider.generateToken(tempClaim, 1000000);
		return success(TestJwtResponseDto.builder().accessToken(accessToken).refreshToken(accessToken).build());
	}

	@GetMapping("/oauth2/login")
	public String oauth() {
		return "oauth2";
	}

	@GetMapping("/login/success")
	public String successRedirect() {
		return "redirecttest";
	}

	@GetMapping("/error")
	public ResponseEntity<String> error() {
		return new ResponseEntity<>("error", HttpStatus.CONFLICT);
	}

}
