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

@Schema(description = "유저의 유저 정보 조회 응답 dto")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFindResponseDto {
	@Schema(description = "유저의 DB Id")
	Long id;
	@Schema(description = "유저의 이름", example = "김흥국")
	String username;
	@Schema(description = "유저의 닉네임", example = "부끄러운 엉덩이")
	String nickname;
	@Schema(description = "프로필사진 리스트", nullable = true)
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
