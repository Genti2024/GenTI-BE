package com.gt.genti.dto.user.response;

import java.util.List;

import com.gt.genti.domain.User;
import com.gt.genti.domain.common.PictureEntity;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindResponseDto {
	@Schema(name = "id")
	Long id;
	@Schema(name = "username")
	String username;
	@Schema(name = "nickname")
	String nickname;
	@Schema(name = "profilePictureList")
	List<CommonPictureResponseDto> profilePictureList;

	@Builder
	public UserFindResponseDto(Long id, String username, String nickname,
		List<CommonPictureResponseDto> profilePictureList) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.profilePictureList = profilePictureList;
	}

	public static UserFindResponseDto of(User user) {
		UserFindResponseDtoBuilder builder = UserFindResponseDto.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.username(user.getUsername());

		if (!user.getPictureProfileList().isEmpty()) {
			builder.profilePictureList(user.getPictureProfileList()
				.stream()
				.map(PictureEntity::mapToCommonResponse)
				.toList());
		}
		return builder.build();
	}
}
