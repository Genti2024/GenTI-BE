package com.gt.genti.other.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;

public class GoogleOAuthAttributes implements OAuthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;
	private final OauthType oauthType = OauthType.GOOGLE;

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
	public OauthType getOauthType() {
		return this.oauthType;
	}
}
