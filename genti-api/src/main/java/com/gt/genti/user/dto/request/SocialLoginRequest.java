package com.gt.genti.user.dto.request;

import com.gt.genti.user.model.OauthPlatform;

public interface SocialLoginRequest {
	OauthPlatform getOauthPlatform();
	String getCode();
}
