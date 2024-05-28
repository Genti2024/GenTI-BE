package com.gt.genti.dto;

import java.util.List;

import com.gt.genti.domain.PictureProfile;
import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;

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

	public UserInfoResponseDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.profilePictureList = user.getPictureProfileList().stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}
}
