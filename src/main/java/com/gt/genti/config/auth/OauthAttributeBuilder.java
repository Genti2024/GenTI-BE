package com.gt.genti.config.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.converter.EnumUtil;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class OauthAttributeBuilder {
	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;

	@Builder
	public OauthAttributeBuilder(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		// this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public static OauthAttributes of(String registrationId, Map<String, Object> attributes) {
		OauthType oauthType = EnumUtil.stringToEnumIgnoreCase(OauthType.class, registrationId);
		switch (oauthType) {
			case GOOGLE -> {
				return new GoogleOauthAttributes(attributes);
			}
			case KAKAO -> {
				return new KakaoOauthAttributes(attributes);
			}
			case APPLE -> {
				return new AppleOauthAttributes(attributes);
			}
			case NULL -> {

			}
		}
		throw new RuntimeException("등록되지 않은 oauth type :" + registrationId);
	}
}
