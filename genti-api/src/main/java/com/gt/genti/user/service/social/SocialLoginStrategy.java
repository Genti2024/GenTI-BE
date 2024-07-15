package com.gt.genti.user.service.social;

import com.gt.genti.auth.dto.request.SocialLoginRequest;
import com.gt.genti.auth.dto.response.SocialLoginResponse;

public interface SocialLoginStrategy {

    SocialLoginResponse login(final SocialLoginRequest request);
    boolean support(String provider);

}
