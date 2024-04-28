package com.gt.genti.config.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;

public class AppleOAuthAttributes implements OAuthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;
	private final OauthType oauthType = OauthType.APPLE;

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
	public OauthType getOauthType() {
		return this.oauthType;
	}

	// @Override
	// public OauthAttribute of(Map<String, Object> attribute) {
	// 	return null;
	// }
}
