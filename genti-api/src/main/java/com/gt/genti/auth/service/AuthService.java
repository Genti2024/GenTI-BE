package com.gt.genti.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.auth.dto.request.KakaoAuthorizationCodeDto;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.auth.dto.request.AppleAuthTokenDto;
import com.gt.genti.auth.social.AppleOauthStrategy;
import com.gt.genti.auth.dto.request.KakaoAccessTokenDto;
import com.gt.genti.auth.social.KakaoOauthStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final AppleOauthStrategy appleOauthStrategy;
	private final KakaoOauthStrategy kakaoOauthStrategy;
	private final JwtTokenProvider jwtTokenProvider;

	public OauthJwtResponse kakaoWebLogin(final KakaoAuthorizationCodeDto request) {
		return kakaoOauthStrategy.webLogin(request);
	}

	public OauthJwtResponse appleLogin(final AppleAuthTokenDto request) {
		return appleOauthStrategy.login(request);
	}

	public OauthJwtResponse kakaoAppLogin(final KakaoAccessTokenDto request) {
		return kakaoOauthStrategy.tokenLogin(request);
	}

	public String getOauthUri(OauthPlatform oauthPlatform) {
		return switch (oauthPlatform) {
			case KAKAO -> kakaoOauthStrategy.getAuthUri();
			// case APPLE -> appleOauthStrategy.getAuthUri();
			default -> null;
		};
	}

	public TokenResponse reissue(TokenRefreshRequestDto tokenRefreshRequestDto) {
		return jwtTokenProvider.reissueIfValid(tokenRefreshRequestDto.getAccessToken(),
			tokenRefreshRequestDto.getRefreshToken());
	}

}
