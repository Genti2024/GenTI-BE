package com.gt.genti.user.service.social;

import com.gt.genti.user.dto.request.SocialLoginRequest;
import com.gt.genti.user.dto.response.SocialLoginResponse;

public interface SocialLoginStrategy {

    SocialLoginResponse login(final SocialLoginRequest request);
    boolean support(String provider);

}
