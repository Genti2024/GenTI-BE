package com.gt.genti.user.model;

import java.util.Map;

public class GoogleOAuthAttributes implements OAuthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;
	private final OauthPlatform oauthPlatform = OauthPlatform.GOOGLE;

	public GoogleOAuthAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.name = (String)attributes.get("name");
		this.email = (String)attributes.get("email");
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public OauthPlatform getOauthType() {
		return this.oauthPlatform;
	}
}
