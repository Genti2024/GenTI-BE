package com.gt.genti.user.model;

public interface OAuthAttributes {
	String getEmail();
	String getUsername();
	OauthPlatform getOauthType();
}
