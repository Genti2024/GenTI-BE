package com.gt.genti.config.auth;

import com.gt.genti.domain.enums.OauthType;

public interface OauthAttributes {
	String getEmail();
	String getUsername();
	OauthType getOauthType();
}
