package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.User;
import com.gt.genti.dto.common.response.CommonPictureUrlResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoUpdateResponseDto {

	Long id;
	String username;
	String nickname;
	List<CommonPictureUrlResponseDto> profilePictureList;

	@Builder
	public UserInfoUpdateResponseDto(Long id, String username, String nickname,
		List<CommonPictureUrlResponseDto> profilePictureList) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.profilePictureList = profilePictureList;
	}
}
