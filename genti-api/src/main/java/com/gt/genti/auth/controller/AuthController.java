package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.auth.api.AuthApi;
import com.gt.genti.auth.dto.request.AppleAuthTokenDto;
import com.gt.genti.auth.dto.request.KakaoAccessTokenDto;
import com.gt.genti.auth.dto.request.KakaoAuthorizationCodeDto;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.AuthUriResponseDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.service.AuthService;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegenerateresponse.service.PGRESCompleteEventPublisher;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
	private final PGRESCompleteEventPublisher pGRESCompleteEventPublisher;

	@GetMapping("/login/oauth2")
	@Logging(item = LogItem.OAUTH_WEB, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<AuthUriResponseDto>> getAuthUri(
		@Parameter(description = "호출할 Oauth platform 종류", example = "KAKAO", schema = @Schema(allowableValues = {
			"KAKAO"}))
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform) {
		return success(AuthUriResponseDto.of(oauthPlatform.getStringValue(), authService.getOauthUri(oauthPlatform)));
	}

	@PostMapping("/login/oauth2/token/apple")
	@Logging(item = LogItem.OAUTH_APPLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginApple(
		@RequestBody @Valid AppleAuthTokenDto request) {
		return success(authService.appleLogin(request));
	}

	@PostMapping("/login/oauth2/token/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginKakao(
		@RequestBody @Valid KakaoAccessTokenDto tokenDto
	) {
		return success(authService.kakaoAppLogin(tokenDto));
	}

	@GetMapping("/login/oauth2/web/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<OauthJwtResponse>> loginKakaoWeb(
		HttpServletResponse response,
		@RequestParam(name = "code") final String code) {
		return success(authService.kakaoWebLogin(KakaoAuthorizationCodeDto.of(code)));
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

	@PostMapping("/fcmtest/userId/{userId}")
	public ResponseEntity<ApiResult<Boolean>> fcmtest(
		@PathVariable Long userId) {
		pGRESCompleteEventPublisher.publishPictureGenerateCompleteEvent(userId);
		return success(true);
	}
}
