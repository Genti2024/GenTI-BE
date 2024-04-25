package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.config.auth.SessionUserDto;
import com.gt.genti.fortest.TestJwtResponseDto;
import com.gt.genti.security.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final JwtUtils jwtUtils;


	@GetMapping("/")
	public String index(Model model) {
		log.info("인덱스 컨트롤러 접근");
		SessionUserDto user = (SessionUserDto)model.getAttribute("user");
		if (user != null) {
			model.addAttribute("userName", user.getName());
		}
		return "oauth";
	}


	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TestJwtResponseDto>> getTestJwt() {
		Map<String, Object> tempClaim = Map.of("email", "user1@gmail.com", "name", "유저테스트1_이름", "userRole", "USER");
		String accessToken = jwtUtils.generateToken(tempClaim, 1000000);
		return success(TestJwtResponseDto.builder().accessToken(accessToken).refreshToken(accessToken).build());
	}

	@GetMapping("/oauth2/login")
	public String oauth() {
		return "oauth";
	}

	@GetMapping("/login/success")
	public String successRedirect(){
		return "redirecttest";
	}

}
