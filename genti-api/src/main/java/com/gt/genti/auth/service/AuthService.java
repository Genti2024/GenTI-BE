package com.gt.genti.auth.service;

import static com.gt.genti.user.service.validator.UserValidator.*;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.jwt.JwtTokenProvider;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.user.model.User;
import com.gt.genti.user.repository.UserRepository;
import com.gt.genti.user.service.social.SocialOauthContext;
import com.gt.genti.util.HttpRequestUtil;

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

	public SocialLoginResponse login(final SocialLoginRequest request) {
		return socialOauthContext.doLogin(request);
	}

	public HttpHeaders getOauthRedirect(OauthPlatform oauthPlatform){
		return HttpRequestUtil.createRedirectHttpHeader(socialOauthContext.getAuthUri(oauthPlatform));
	}

	public Boolean logout(final Long userId) {
		User foundUser = getUserByUserId(userId);
		validateUserAuthorization(foundUser.getId(), userId);
		jwtTokenProvider.deleteRefreshToken(userId);
		return true;
	}

	private User getUserByUserId(final Long userId){
		return userRepository.findById(userId).orElseThrow(()-> ExpectedException.withLogging(ResponseCode.UserNotFound));
	}

}
