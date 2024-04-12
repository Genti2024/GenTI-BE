package com.gt.genti.dto;

import com.gt.genti.domain.Picture;
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
	public UserInfoResponseDto(User user, Picture picture){
		this.id = user.getId();
		this.username = user.getUsername();
		this.pictureId = picture.getId();
		this.url = picture.getUrl();
	}
}
