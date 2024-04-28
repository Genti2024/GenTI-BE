package com.gt.genti.config.auth;

import java.util.Map;
import java.util.UUID;

import com.gt.genti.domain.enums.OauthType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoOauthAttributes implements OauthAttributes {

	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String nickname;
	private String email;
	private final OauthType oauthType = OauthType.KAKAO;

	public KakaoOauthAttributes(Map<String, Object> attributes) {
		log.info("attributes.toString() : " + attributes.toString());
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
	public OauthType getOauthType() {
		return this.oauthType;
	}
}
