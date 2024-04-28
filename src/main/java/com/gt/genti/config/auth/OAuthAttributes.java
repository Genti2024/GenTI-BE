package com.gt.genti.config.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.converter.EnumUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		OauthType oauthType = EnumUtil.stringToEnum(OauthType.class, registrationId);
		switch (oauthType) {
			case GOOGLE -> {
				return ofGoogle(userNameAttributeName, attributes);
			}
			case KAKAO -> {
				// return ofGoogle(userNameAttributeName, attributes);
			}
			case APPLE -> {
				// return ofGoogle(userNameAttributeName, attributes);
			}
			case NULL -> {

			}
		}
		throw new RuntimeException("등록되지 않은 oauth type? ");
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

}
