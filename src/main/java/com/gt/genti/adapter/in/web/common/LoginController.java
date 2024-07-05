package com.gt.genti.adapter.in.web.common;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.application.service.UserService;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.fortest.TestJwtResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.security.JwtTokenProvider;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@GetMapping("/oauth2")
	public String index() {
		return "oauth2";
	}

	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TestJwtResponseDto>> getTestJwt(
		@NotNull @PathParam(value = "role") UserRole role) {
		Map<UserRole, String> userIdMapper = Map.of(UserRole.USER, "2", UserRole.ADMIN, "1", UserRole.CREATOR, "4");
		String userId = userIdMapper.get(role);
		Map<String, Object> tempClaim = Map.of("auth", role.getRoleString(), "sub", userId);
		String accessToken = jwtTokenProvider.generateToken(tempClaim, 1000000);
		return success(TestJwtResponseDto.builder().accessToken(accessToken).refreshToken(accessToken).build());
	}

	@GetMapping("/oauth2/login")
	public String oauth() {
		return "oauth2";
	}

	@GetMapping("/login/success")
	public String successRedirect() {
		return "로그인 성공";
	}

	@GetMapping("/logout")
	public ResponseEntity<ApiResult<Boolean>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.logout(userDetails));
	}
}
