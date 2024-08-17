package com.gt.genti.user;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gt.genti.user.model.User;

public class UserSerializer extends StdSerializer<User> {

	public UserSerializer() {
		this(null);
	}

	public UserSerializer(Class<User> t) {
		super(t);
	}

	@Override
	public void serialize(User user, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", user.getId());
		gen.writeStringField("socialId", user.getSocialId());
		gen.writeStringField("imageUrl", user.getOauthImageUrl());
		gen.writeStringField("email", user.getOauthImageUrl());
		gen.writeStringField("sex", user.getSex().getStringValue());
		gen.writeStringField("introduction", user.getIntroduction());
		gen.writeStringField("username", user.getUsername());
		gen.writeStringField("nickname", user.getNickname());
		gen.writeStringField("userStatus", user.getUserStatus().getStringValue());
		gen.writeBooleanField("emailVerified", user.getEmailVerified());
		gen.writeStringField("loginId", user.getLoginId());
		gen.writeStringField("password", user.getPassword());
		gen.writeStringField("userRole", user.getUserRole().getStringValue());
		gen.writeStringField("lastLoginOauthPlatform", user.getLastLoginOauthPlatform().getStringValue());
		if (user.getDeletedAt() != null) {
			gen.writeStringField("deletedAt", user.getDeletedAt().toString());
		}
		if (user.getLastLoginDate() != null) {
			gen.writeStringField("lastLoginDate", user.getLastLoginDate().toString());
		}
		gen.writeNumberField("requestTaskCount", user.getRequestTaskCount());
		if (user.getBirthYear() != null) {
			gen.writeStringField("birthDate", user.getBirthYear().toString());
		}
		gen.writeEndObject();

	}
}
