package com.gt.genti.auth.service;

import static com.gt.genti.user.service.validator.UserValidator.*;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.auth.dto.request.SocialAppLoginRequest;
import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.request.TokenRefreshRequestDto;
import com.gt.genti.auth.dto.response.OauthJwtResponse;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.jwt.TokenResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.social.SocialOauthContext;
import com.gt.genti.util.HttpRequestUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final SocialOauthContext socialOauthContext;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	public SocialLoginResponse webLogin(final SocialLoginRequest request) {
		return socialOauthContext.doLogin(request);
	}

	public OauthJwtResponse appLogin(final @Valid SocialAppLoginRequest request) {
		return socialOauthContext.doAppLogin(request).getToken();
	}

	public HttpHeaders getOauthRedirect(OauthPlatform oauthPlatform) {
		return HttpRequestUtil.createRedirectHttpHeader(socialOauthContext.getAuthUri(oauthPlatform));
	}

	public Boolean logout(final Long userId) {
		User foundUser = getUserByUserId(userId);
		validateUserAuthorization(foundUser.getId(), userId);
		jwtTokenProvider.deleteRefreshToken(userId);
		return true;
	}

	private User getUserByUserId(final Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.UserNotFound));
	}

	public TokenResponse reissue(TokenRefreshRequestDto tokenRefreshRequestDto) {
		return jwtTokenProvider.reissueIfValid(tokenRefreshRequestDto.getAccessToken(),
			tokenRefreshRequestDto.getRefreshToken());
	}
}
