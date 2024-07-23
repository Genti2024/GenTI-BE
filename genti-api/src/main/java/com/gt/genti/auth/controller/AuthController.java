package com.gt.genti.auth.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.Map;

import com.gt.genti.auth.dto.response.KakaoJwtResponse;
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
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenGenerateCommand;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.auth.dto.request.AppleLoginRequest;
import com.gt.genti.auth.dto.request.KakaoJwtCreateRequestDTO;
import com.gt.genti.auth.dto.request.SocialLoginRequestImpl;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	@Operation(summary = "oauth 로그인 페이지 호출", description = "구글, 카카오 oauth로그인페이지 로 Redirect 됩니다. url은 HttpHeader에 포함되어있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "303", description = "리디렉션을 위한 응답 헤더를 포함합니다.", headers = {
			@Header(name = "Location", description = "리디렉션 URL", schema = @Schema(type = "string"))
		}, content = @Content(schema = @Schema(hidden = true)))
	})
	@GetMapping("/login/v1/oauth2")
	@Logging(item = LogItem.OAUTH, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<Object> login(
		@Parameter(description = "호출할 Oauth platform 종류", example = "KAKAO", schema = @Schema(allowableValues = {
			"KAKAO"}))
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform) {
		HttpHeaders httpHeaders = authService.getOauthRedirect(oauthPlatform);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}

	@Operation(summary = "apple 로그인 후 토큰으로 로그인", description = "앱에서 apple 로그인을 수행한 후 받은 token을 전달받아 apple auth 서버와 통신 후 로그인 처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.AppleOauthClaimInvalid),
		@EnumResponse(ResponseCode.AppleOauthIdTokenExpired),
		@EnumResponse(ResponseCode.AppleOauthIdTokenIncorrect),
		@EnumResponse(ResponseCode.AppleOauthIdTokenInvalid),
		@EnumResponse(ResponseCode.AppleOauthJwtValueInvalid),
		@EnumResponse(ResponseCode.AppleOauthPublicKeyInvalid),
	})
	@PostMapping("/login/v1/oauth2/code/apple")
	@Logging(item = LogItem.OAUTH_APPLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> loginApple(
		@RequestBody @Valid AppleLoginRequestDto request) {
		return success(authService.login(AppleLoginRequest.of(OauthPlatform.APPLE, request.getToken())));
	}

	@Operation(summary = "kakao 로그인 후 redirect url", description = "FE에서 직접 호출할 일은 없습니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
	})
	@GetMapping("/login/v1/oauth2/code/kakao")
	@Logging(item = LogItem.OAUTH_KAKAO, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> kakaoLogin(
		@RequestParam(name = "code") final String code) {
		return success(authService.login(SocialLoginRequestImpl.of(OauthPlatform.KAKAO, code)));
	}

	@Deprecated
	@Operation(summary = "google 로그인 후 redirect url", description = "FE에서 직접 호출할 일은 없습니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
	})
	@GetMapping("/login/v1/oauth2/code/google")
	@Logging(item = LogItem.OAUTH_GOOGLE, action = LogAction.LOGIN, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SocialLoginResponse>> googleLogin(
		@RequestParam(name = "code") final String code) {
		return success(authService.login(SocialLoginRequestImpl.of(OauthPlatform.GOOGLE, code)));
	}

	@Operation(summary = "테스트용 jwt 토큰 발급", description = "")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/login/testjwt")
	public ResponseEntity<ApiResult<TokenResponse>> getTestJwt(
		@NotNull @RequestParam(name = "role", value = "role") UserRole role) {
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

	@Operation(summary = "로그아웃", description = "refreshToken 삭제")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.Forbidden),
		@EnumResponse(ResponseCode.TOKEN_REFRESH_FAILED),
	})
	@GetMapping("/logout/v1")
	public ResponseEntity<ApiResult<Boolean>> logout(@AuthUser Long userId) {
		return success(authService.logout(userId));
	}

	@Operation(summary = "카카오 사용자 확인 후 jwt 토큰 발급", description = "카카오 사용자가 기존 회원이면 jwt 토큰 발급, 신규 회원이면 회원가입 후 jwt 토큰 발급")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/auth/jwt/kakao/v1")
	@Logging(item = LogItem.JWT, action = LogAction.CREATE, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<KakaoJwtResponse>> createJwt(
		@RequestBody @Valid KakaoJwtCreateRequestDTO kakaoJwtCreateRequestDTO) {
		return success(authService.createJwt(kakaoJwtCreateRequestDTO));
	}
}
