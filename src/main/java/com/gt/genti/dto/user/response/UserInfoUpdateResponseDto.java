package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateResponseDto {

	Long id;
	String username;
	String nickname;
	List<CommonPictureResponseDto> profilePictureList;

	@Builder
	public UserInfoUpdateResponseDto(Long id, String username, String nickname,
		List<CommonPictureResponseDto> profilePictureList) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.profilePictureList = profilePictureList;
	}
}
