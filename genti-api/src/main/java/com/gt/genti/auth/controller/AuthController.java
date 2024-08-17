package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.gt.genti.auth.api.AuthApi;
import com.gt.genti.auth.dto.request.KakaoAuthorizationCodeDto;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialWebLoginResponse;
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
import com.gt.genti.auth.dto.request.AppleAuthTokenDto;
import com.gt.genti.auth.dto.request.KakaoAccessTokenDto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.Cookie;
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

	@PostMapping("/login/oauth2/token/apple")
	@Logging(item = LogItem.OAUTH_APPLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginApple(
		@RequestBody @Valid AppleAuthTokenDto request) {
		return success(authService.appleLogin(request).token());
	}

	@PostMapping("/login/oauth2/token/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginKakao(
		@RequestBody @Valid KakaoAccessTokenDto tokenDto
	) {
		return success(authService.kakaoAppLogin(tokenDto));
	}

	@GetMapping("/login/oauth2/code/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public void kakaoRedirectLogin(
		HttpServletResponse response,
		@RequestParam(name = "code") final String code) {
		SocialWebLoginResponse socialWebLoginResponse = authService.kakaoWebLogin(KakaoAuthorizationCodeDto.of(code));
		String accessToken = socialWebLoginResponse.getToken().accessToken();
		String refreshToken = socialWebLoginResponse.getToken().refreshToken();

		String accessTokenEncode = URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
		String refreshTokenEncode = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

		Cookie accessTokenCookie = new Cookie("Access-Token", accessTokenEncode);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setPath("/");

		Cookie refreshTokenCookie = new Cookie("Refresh-Token", refreshTokenEncode);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setPath("/");

		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);
		try {
			response.sendRedirect("http://localhost:5173/login/kakao/success");
		} catch (IOException e) {
			throw new RuntimeException("서버에서 redirect중 에러가 발생했습니다.");
		}
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

	@PostMapping("/reissue")
	public ResponseEntity<ApiResult<TokenResponse>> reissue(
		@RequestBody @Valid TokenRefreshRequestDto tokenRefreshRequestDto
	) {
		return success(authService.reissue(tokenRefreshRequestDto));
	}

}
