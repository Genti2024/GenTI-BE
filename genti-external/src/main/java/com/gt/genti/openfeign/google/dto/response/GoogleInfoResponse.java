package com.gt.genti.openfeign.google.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Deprecated
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleInfoResponse(
	String sub,
	String name,
	String givenName,
	String familyName,
	String picture,
	String email,
	Boolean emailVerified,
	String locale,
	String birthday
) {
	public static GoogleInfoResponse of(String sub, String name, String givenName, String familyName, String picture,
		String email, Boolean emailVerified, String locale, String birthday) {
		return new GoogleInfoResponse(sub, name, givenName, familyName, picture, email, emailVerified, locale,
			birthday);
	}
}