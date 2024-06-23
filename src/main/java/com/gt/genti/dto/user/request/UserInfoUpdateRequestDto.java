package com.gt.genti.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "ㅁㄴㅇㄹ")

public class UserInfoUpdateRequestDto {
	@Schema(name = "userName")
	String userName;
	@Schema(name = "profilePictureUrl")
	String profilePictureUrl;

	@Builder
	public UserInfoUpdateRequestDto(String userName, String profilePictureUrl) {
		this.userName = userName;
		this.profilePictureUrl = profilePictureUrl;
	}
}
