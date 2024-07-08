package com.gt.genti.user.service.social;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gt.genti.user.model.OauthPlatform;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocialLoginContext {

    private final GoogleLoginStrategy googleLoginStrategy;
    private final KakaoLoginStrategy kakaoLoginStrategy;
    private final List<SocialLoginStrategy> socialLoginStrategies = new ArrayList<>();

    @PostConstruct
    void initSocialLoginContext() {
        socialLoginStrategies.add(googleLoginStrategy);
        socialLoginStrategies.add(kakaoLoginStrategy);
    }

    public boolean support(OauthPlatform platform) {
        for (SocialLoginStrategy strategy : socialLoginStrategies) {
            if (strategy.support(platform.toString())) {
                return true;
            }
        }
        return false;
    }

    public SocialLoginResponse doLogin(final SocialLoginRequest request) {
        for (SocialLoginStrategy strategy : socialLoginStrategies) {
            if (strategy.support(request.oauthPlatform().toString())) {
                return strategy.login(request);
            }
        }
        throw ExpectedException.withLogging(ResponseCode.OauthProviderNotAllowed);
    }

}
