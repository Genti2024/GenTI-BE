package com.gt.genti.user.model;

import java.util.Map;

public class AppleOAuthAttributes implements OAuthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;
	private final OauthPlatform oauthPlatform = OauthPlatform.APPLE;

	public AppleOAuthAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.name = (String)attributes.get("name");
		this.email = (String)attributes.get("email");
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public OauthPlatform getOauthType() {
		return this.oauthPlatform;
	}

	// @Override
	// public OauthAttribute of(Map<String, Object> attribute) {
	// 	return null;
	// }
}
