package com.gt.genti.other.auth;

import java.util.Map;

import com.gt.genti.domain.enums.OauthType;
import com.gt.genti.domain.enums.converter.EnumUtil;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class OAuthAttributeBuilder {
	private Map<String, Object> attributes;
	// private String nameAttributeKey;
	private String name;
	private String email;

	@Builder
	public OAuthAttributeBuilder(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		// this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
		OauthType oauthType = EnumUtil.stringToEnumIgnoreCase(OauthType.class, registrationId);
		switch (oauthType) {
			case GOOGLE -> {
				return new GoogleOAuthAttributes(attributes);
			}
			case KAKAO -> {
				return new KakaoOAuthAttributes(attributes);
			}
			case APPLE -> {
				return new AppleOAuthAttributes(attributes);
			}
			case NONE -> {

			}
		}
		throw new ExpectedException(ErrorCode.NotAllowedOauthProvider,registrationId );
	}
}
