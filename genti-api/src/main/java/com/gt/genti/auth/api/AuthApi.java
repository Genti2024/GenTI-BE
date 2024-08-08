package com.gt.genti.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.auth.dto.request.AppleLoginRequestDto;
import com.gt.genti.auth.dto.request.SocialAppLoginRequest;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.UserRole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

;

@Tag(name = "[AuthController] 인증 컨트롤러", description = "로그인을 처리한 후 토큰을 전달합니다.")
public interface AuthApi {
	@Operation(summary = "oauth 로그인 페이지 호출", description = "구글, 카카오 oauth로그인페이지 로 Redirect 됩니다. url은 HttpHeader에 포함되어있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "303", description = "리디렉션을 위한 응답 헤더를 포함합니다.", headers = {
			@Header(name = "Location", description = "리디렉션 URL", schema = @Schema(type = "string"))
		}, content = @Content(schema = @Schema(hidden = true)))
	})
	ResponseEntity<Object> login(
		@Parameter(description = "호출할 Oauth platform 종류", example = "KAKAO", schema = @Schema(allowableValues = {
			"KAKAO"}))
		@RequestParam(name = "oauthPlatform") OauthPlatform oauthPlatform);

	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.AppleOauthClaimInvalid),
		@EnumResponse(ResponseCode.AppleOauthIdTokenExpired),
		@EnumResponse(ResponseCode.AppleOauthIdTokenIncorrect),
		@EnumResponse(ResponseCode.AppleOauthIdTokenInvalid),
		@EnumResponse(ResponseCode.AppleOauthJwtValueInvalid),
		@EnumResponse(ResponseCode.AppleOauthPublicKeyInvalid),
	})
	ResponseEntity<ApiResult<SocialLoginResponse>> loginApple(
		@RequestBody @Valid AppleLoginRequestDto request);

	ResponseEntity<ApiResult<SocialLoginResponse>> kakaoLogin(
		@RequestParam(name = "code") String code);

	@Deprecated
	ResponseEntity<ApiResult<SocialLoginResponse>> googleLogin(
		@RequestParam(name = "code") String code);

	@Operation(summary = "테스트용 jwt 토큰 발급", description = "")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<TokenResponse>> getTestJwt(
		@NotNull @RequestParam(name = "role", value = "role") UserRole role);

	@Operation(summary = "oauth platform에서 로그인 후 받은 토큰을 전달하여 가입/로그인", description = "현재 애플, 카카오 지원합니다")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.AppleOauthClaimInvalid),
		@EnumResponse(ResponseCode.AppleOauthIdTokenExpired),
		@EnumResponse(ResponseCode.AppleOauthIdTokenIncorrect),
		@EnumResponse(ResponseCode.AppleOauthIdTokenInvalid),
		@EnumResponse(ResponseCode.AppleOauthJwtValueInvalid),
		@EnumResponse(ResponseCode.AppleOauthPublicKeyInvalid),
	})
	ResponseEntity<ApiResult<OauthJwtResponse>> loginOrSignUpWithOAuthToken(
		@RequestBody @Valid SocialAppLoginRequest socialAppLoginRequest);

	ResponseEntity<ApiResult<TokenResponse>> reissue(
		@RequestBody @Valid TokenRefreshRequestDto tokenRefreshRequestDto
	);
}
