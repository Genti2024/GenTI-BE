package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.response.CommonPictureUrlResponseDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindResponseDto {
	Long id;
	String username;
	String nickname;
	List<CommonPictureUrlResponseDto> profilePictureList;

	public UserFindResponseDto(Long id, String username, String nickname,
		List<CommonPictureUrlResponseDto> profilePictureList) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.profilePictureList = profilePictureList;
	}
}
