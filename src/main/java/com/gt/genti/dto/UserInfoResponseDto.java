package com.gt.genti.dto;

import com.gt.genti.domain.PictureCreated;
import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {
	Long id;
	String username;
	Long pictureId;
	String url;

	@Builder
	public UserInfoResponseDto(User user, PictureProfile pictureProfile){
		this.id = user.getId();
		this.username = user.getUsername();
		this.pictureId = pictureProfile.getId();
		this.url = pictureProfile.getUrl();
	}
}
