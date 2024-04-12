package com.gt.genti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateRequestDto {
	String userName;
	String profilePictureUrl;

	@Builder
	public UserInfoUpdateRequestDto(String userName, String profilePictureUrl) {
		this.userName = userName;
		this.profilePictureUrl = profilePictureUrl;
	}
}
