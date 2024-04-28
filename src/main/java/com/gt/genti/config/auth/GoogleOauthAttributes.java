package com.gt.genti.config.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;

public class GoogleOauthAttributes implements OauthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;
	private final OauthType oauthType = OauthType.GOOGLE;

	public GoogleOauthAttributes(Map<String, Object> attributes) {
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
