package com.gt.genti.login.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gt.genti.fortest.TestJwtResponseDto;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.service.UserService;

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
		TokenGenerateCommand command = TokenGenerateCommand.builder()
			.userId(userId)
			.role(role.getRoles())
			.build();
		String accessToken = jwtTokenProvider.generateAccessToken(command);
		return success(
			TestJwtResponseDto.builder().accessToken(accessToken).refreshToken(accessToken).build());
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
	public ResponseEntity<ApiResult<Boolean>> logout(@AuthUser Long userId) {
		return success(userService.logout(userId));
	}
}
