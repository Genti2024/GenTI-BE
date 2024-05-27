package com.gt.genti.dto;

import java.util.List;

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
	List<CommonPictureResponseDto> profilePictureList;

	@Builder
	public UserInfoResponseDto(User user, List<PictureProfile> pictureProfileList) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.profilePictureList = pictureProfileList.stream()
			.map(pictureProfile -> new CommonPictureResponseDto(pictureProfile.getId(), pictureProfile.getUrl()))
			.toList();
	}
}
