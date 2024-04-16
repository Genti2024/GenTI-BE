package com.gt.genti.config.auth;

import java.util.Map;

import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.UserStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
		String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.picture((String)attributes.get("picture"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	// oauth 신규가입
	public User toEntity() {
		return User.builder()
			.username(name)
			.email(email)
			.oauthPictureUrl(picture)
			.userRole(UserRole.USER)
			.userStatus(UserStatus.ACTIVATED)
			.build();
	}
}
