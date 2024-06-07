package com.gt.genti.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoUpdateRequestDto {
	String userName;
	String profilePictureUrl;

	@Builder
	public UserInfoUpdateRequestDto(String userName, String profilePictureUrl) {
		this.userName = userName;
		this.profilePictureUrl = profilePictureUrl;
	}
}
