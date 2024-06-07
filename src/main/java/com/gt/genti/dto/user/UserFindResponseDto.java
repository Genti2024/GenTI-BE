package com.gt.genti.dto.user;

import java.util.List;

import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.CommonPictureUrlResponseDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindResponseDto {
	Long id;
	String username;
	List<CommonPictureUrlResponseDto> profilePictureList;

	public UserFindResponseDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.profilePictureList = user.getPictureProfileList().stream()
			.map(PictureEntity::mapToCommonResponse)
			.toList();
	}
}
