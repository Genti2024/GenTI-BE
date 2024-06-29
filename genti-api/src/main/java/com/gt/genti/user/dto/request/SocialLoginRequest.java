package com.gt.genti.user.dto.request;

import com.gt.genti.user.model.OauthPlatform;

public record SocialLoginRequest(
        OauthPlatform oauthPlatform,
        String code
) {
    public static SocialLoginRequest of(OauthPlatform oauthPlatform, String code) {
        return new SocialLoginRequest(oauthPlatform, code);
    }
}
