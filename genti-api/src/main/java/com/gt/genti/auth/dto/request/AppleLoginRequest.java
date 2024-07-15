package com.gt.genti.auth.dto.request;

import com.gt.genti.user.model.OauthPlatform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AppleLoginRequest implements SocialLoginRequest {
    @Getter
    final OauthPlatform oauthPlatform;

    final String token;

    public String getCode(){
        return token;
    }
    public static SocialLoginRequest of(OauthPlatform oauthPlatform, String token) {
        return new AppleLoginRequest(oauthPlatform, token);
    }
}
