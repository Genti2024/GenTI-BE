package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema(description = "유저의 정보 수정 요청 성공시 응답")
@Getter
@NoArgsConstructor
public class UserInfoUpdateResponseDto {
	@Schema(name = "id", description = "id")
	Long id;
	@Schema(name = "username")
	String username;
	@Schema(name = "nickname")
	String nickname;
	@Schema(name = "profilePictureList")
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
