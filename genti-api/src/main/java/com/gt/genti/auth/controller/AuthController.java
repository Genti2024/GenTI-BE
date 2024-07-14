package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.auth.dto.request.AppleLoginRequestDto;
import com.gt.genti.auth.service.AuthService;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.user.dto.request.AppleLoginRequest;
import com.gt.genti.user.dto.request.SocialLoginRequestImpl;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthService authService;

	@GetMapping("/login/v1/oauth")
	@Logging(item = LogItem.OAUTH, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<Object> login(
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform) {
		HttpHeaders httpHeaders = authService.getOauthRedirect(oauthPlatform);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@PostMapping("/login/v1/oauth2/code/apple")
	@Logging(item = LogItem.OAUTH_APPLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> loginApple(@RequestBody @Valid AppleLoginRequestDto request) {
		return success(authService.login(AppleLoginRequest.of(OauthPlatform.APPLE, request.getToken())));
	}

	@GetMapping("/login/v1/oauth2/code/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> kakaoLogin(
		@RequestParam(name = "code") final String code) {
		return success(authService.login(SocialLoginRequestImpl.of(OauthPlatform.KAKAO, code)));
	}

	@GetMapping("/login/v1/oauth2/code/google")
	@Logging(item = LogItem.OAUTH_GOOGLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> googleLogin(
		@RequestParam(name = "code") final String code) {
		return success(authService.login(SocialLoginRequestImpl.of(OauthPlatform.GOOGLE, code)));
	}

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

	@GetMapping("/logout/v1")
	public ResponseEntity<ApiResult<Boolean>> logout(@AuthUser Long userId) {
		return success(authService.logout(userId));
	}
}
