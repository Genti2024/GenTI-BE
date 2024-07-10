package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.model.Logging;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.service.UserService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@GetMapping("/v1/login")
	@Logging(item = "Oauth", action = "Get")
	public ResponseEntity<Object> login(
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform) {
		HttpHeaders httpHeaders = userService.getOauthRedirect(oauthPlatform);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@GetMapping("/login/oauth2/code/kakao")
	@Logging(item = "Oauth", action = "Post")
	public ResponseEntity<ApiResult<SocialLoginResponse>> kakaoLogin(
		@RequestParam(name = "code") final String code) {
		return success(userService.login(SocialLoginRequest.of(OauthPlatform.KAKAO, code)));
	}

	@GetMapping("/login/oauth2/code/google")
	@Logging(item = "Oauth", action = "Post")
	public ResponseEntity<ApiResult<SocialLoginResponse>> googleLogin(
		@RequestParam(name = "code") final String code) {
		return success(userService.login(SocialLoginRequest.of(OauthPlatform.GOOGLE, code)));
	}

	@Logging(item = "jwt", action = "Get")
	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TokenResponse>> getTestJwt(
		@NotNull @RequestParam(name = "role", value = "role") UserRole role) {
		log.info("요청 ");
		Map<UserRole, String> userIdMapper = Map.of(UserRole.USER, "2", UserRole.ADMIN, "1", UserRole.CREATOR, "4");
		String userId = userIdMapper.get(role);
		TokenGenerateCommand command = TokenGenerateCommand.builder()
			.userId(userId)
			.role(role.getRoles())
			.build();
		String accessToken = jwtTokenProvider.generateAccessToken(command);

		return success(
			TokenResponse.of(accessToken, accessToken));
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
