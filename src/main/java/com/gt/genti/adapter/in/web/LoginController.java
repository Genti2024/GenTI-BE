package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.fortest.TestJwtResponseDto;
import com.gt.genti.other.security.JwtTokenProvider;

import jakarta.websocket.server.PathParam;
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
	public ResponseEntity<ApiResult<TestJwtResponseDto>> getTestJwt(@PathParam(value = "role") String role) {
		UserRole tempRole = null;
		for (UserRole userRole : UserRole.class.getEnumConstants()) {
			if (userRole.getStringValue().contains(role.toUpperCase())) {
				tempRole = userRole;
				break;
			}
		}
		if (tempRole == null) {
			throw new RuntimeException();
		}
		Map<UserRole, String> userIdMapper = Map.of(UserRole.USER, "3", UserRole.ADMIN, "1", UserRole.CREATOR, "4");
		String userId = userIdMapper.get(tempRole);
		Map<String, Object> tempClaim = Map.of("auth", tempRole.getStringValue(), "sub", userId);
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

	// @GetMapping("/error")
	// public ResponseEntity<String> error() {
	//
	// 	return new ResponseEntity<>("error", HttpStatus.CONFLICT);
	// }

}
