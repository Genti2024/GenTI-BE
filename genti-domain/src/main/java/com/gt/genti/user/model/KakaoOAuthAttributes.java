package com.gt.genti.user.model;

import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoOAuthAttributes implements OAuthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String nickname;
	private String email;
	private final OauthPlatform oauthPlatform = OauthPlatform.KAKAO;

	public KakaoOAuthAttributes(Map<String, Object> attributes) {
		log.info("attributes.toString() : " + attributes.toString());
		@SuppressWarnings(value = "unchecked")
		Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");
		this.nickname = (String)properties.get("nickname");
		log.info("this.nickname : " + this.nickname);
		this.email = UUID.randomUUID().toString();
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public String getUsername() {
		return this.nickname;
	}

	@Override
	public OauthPlatform getOauthType() {
		return this.oauthPlatform;
	}
}
