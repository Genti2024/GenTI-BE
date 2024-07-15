package com.gt.genti.auth.dto.request;

import com.gt.genti.user.model.OauthPlatform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SocialLoginRequestImpl implements SocialLoginRequest{
    final OauthPlatform oauthPlatform;
    final String code;
    public static SocialLoginRequest of(OauthPlatform oauthPlatform, String code) {
        return new SocialLoginRequestImpl(oauthPlatform, code);
    }
}
