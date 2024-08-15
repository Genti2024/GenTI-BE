package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.gt.genti.auth.api.AuthApi;
import com.gt.genti.auth.dto.request.AppleLoginRequest;
import com.gt.genti.auth.dto.request.AppleLoginRequestDto;
import com.gt.genti.auth.dto.request.SocialAppLoginRequest;
import com.gt.genti.auth.dto.request.SocialLoginRequestImpl;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.auth.service.AuthService;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController implements AuthApi {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthService authService;

	@GetMapping("/login/oauth2")
	@Logging(item = LogItem.OAUTH_WEB, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public RedirectView login(
		@Parameter(description = "호출할 Oauth platform 종류", example = "KAKAO", schema = @Schema(allowableValues = {
			"KAKAO"}))
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform) {
		return new RedirectView(authService.getOauthRedirect(oauthPlatform));
	}

	@PostMapping("/login/oauth2/code/apple")
	@Logging(item = LogItem.OAUTH_APPLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> loginApple(
		@RequestBody @Valid AppleLoginRequestDto request) {
		return success(authService.webLogin(AppleLoginRequest.of(OauthPlatform.APPLE, request.getToken())));
	}

	@GetMapping("/login/oauth2/code/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public void kakaoLogin(
		HttpServletResponse response,
		@RequestParam(name = "code") final String code) {
		SocialLoginResponse socialLoginResponse = authService.webLogin(SocialLoginRequestImpl.of(OauthPlatform.KAKAO, code));
		String accessToken = socialLoginResponse.getToken().accessToken();
		String refreshToken = socialLoginResponse.getToken().refreshToken();
		response.setHeader("Access-Token",accessToken);
		response.setHeader("Refresh-Token",refreshToken);
		try {
			response.sendRedirect("http://localhost:5173/login/kakao/success");
		} catch (IOException e) {
			throw new RuntimeException("서버에서 redirect중 에러가 발생했습니다.");
		}
	}

	@Deprecated
	@GetMapping("/login/oauth2/code/google")
	@Logging(item = LogItem.OAUTH_GOOGLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> googleLogin(
		@RequestParam(name = "code") final String code) {
		return success(authService.webLogin(SocialLoginRequestImpl.of(OauthPlatform.GOOGLE, code)));
	}

	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TokenResponse>> getTestJwt(
		@NotNull @RequestParam(name = "role", value = "role") UserRole role) {
		Map<UserRole, String> userIdMapper = Map.of(UserRole.USER, "2", UserRole.ADMIN, "1", UserRole.CREATOR, "4",
			UserRole.OAUTH_FIRST_JOIN, String.valueOf(Long.MAX_VALUE));
		String userId = userIdMapper.get(role);
		TokenGenerateCommand command = TokenGenerateCommand.builder()
			.userId(userId)
			.role(role.getRoles())
			.build();
		String accessToken = jwtTokenProvider.generateAccessToken(command);
		String refreshToken = jwtTokenProvider.generateRefreshToken(command);
		return success(
			TokenResponse.of(accessToken, refreshToken));
	}

	@PostMapping("/login/oauth2/token")
	@Logging(item = LogItem.OAUTH_APP, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginOrSignUpWithOAuthToken(
		@RequestBody @Valid SocialAppLoginRequest socialAppLoginRequest) {
		return success(authService.appLogin(socialAppLoginRequest));
	}

	@PostMapping("/reissue")
	public ResponseEntity<ApiResult<TokenResponse>> reissue(
		@RequestBody @Valid TokenRefreshRequestDto tokenRefreshRequestDto
	) {
		return success(authService.reissue(tokenRefreshRequestDto));
	}

}
