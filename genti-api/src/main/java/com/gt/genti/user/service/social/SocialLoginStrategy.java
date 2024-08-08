package com.gt.genti.user.service.social;

import com.gt.genti.auth.dto.request.SocialAppLoginRequest;
import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.response.SocialLoginResponse;

public interface SocialLoginStrategy {

    SocialLoginResponse webLogin(final SocialLoginRequest request);
    SocialLoginResponse tokenLogin(final SocialAppLoginRequest request);
    boolean support(String provider);

}
