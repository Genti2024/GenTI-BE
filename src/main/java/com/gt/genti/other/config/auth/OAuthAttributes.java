package com.gt.genti.other.config.auth;

import com.gt.genti.domain.enums.OauthType;

public interface OAuthAttributes {
	String getEmail();
	String getUsername();
	OauthType getOauthType();
}
