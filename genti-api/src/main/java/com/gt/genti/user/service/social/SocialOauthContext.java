package com.gt.genti.user.service.social;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.response.SocialLoginResponse;
import com.gt.genti.user.model.OauthPlatform;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocialOauthContext {

	private final GoogleOauthStrategy googleOauthStrategy;
	private final KakaoOauthStrategy kakaoOauthStrategy;
	private final AppleOauthStrategy appleOauthStrategy;
	private final List<SocialLoginStrategy> socialLoginStrategies = new ArrayList<>();

	@PostConstruct
	void initSocialLoginContext() {
		socialLoginStrategies.add(googleOauthStrategy);
		socialLoginStrategies.add(kakaoOauthStrategy);
		socialLoginStrategies.add(appleOauthStrategy);
	}

	private SocialLoginStrategy loginStrategyOf(OauthPlatform oauthPlatform) {
		for (SocialLoginStrategy strategy : socialLoginStrategies) {
			if (strategy.support(oauthPlatform.toString())) {
				return strategy;
			}
		}
		throw ExpectedException.withLogging(ResponseCode.OauthProviderNotAllowed);
	}

	private SocialAuthStrategy authStrategyOf(OauthPlatform oauthPlatform) {
		SocialLoginStrategy strategy = loginStrategyOf(oauthPlatform);
		if (strategy instanceof SocialAuthStrategy) {
			return (SocialAuthStrategy)strategy;
		}
		throw ExpectedException.withLogging(ResponseCode.OauthProviderNotAllowed);
	}

	public SocialLoginResponse doLogin(final SocialLoginRequest request) {
		return loginStrategyOf(request.getOauthPlatform()).login(request);
	}

	public String getAuthUri(OauthPlatform oauthPlatform) {
		return authStrategyOf(oauthPlatform).getAuthUri();
	}

}
