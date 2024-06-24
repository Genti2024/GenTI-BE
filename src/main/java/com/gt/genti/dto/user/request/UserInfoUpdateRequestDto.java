package com.gt.genti.dto.user.request;

import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "사용자 정보 수정 요청 dto")
public class UserInfoUpdateRequestDto {
	@Schema(description = "실제 이름", example = "김흥국")
	String userName;

	@Schema(description = "닉네임", example = "신난 선풍기")
	String nickName;

	@Schema(description = "프로필사진")
	CommonPictureKeyUpdateRequestDto profilePicture;

	@Builder
	public UserInfoUpdateRequestDto(String userName, String nickName, CommonPictureKeyUpdateRequestDto profilePicture) {
		this.userName = userName;
		this.nickName = nickName;
		this.profilePicture = profilePicture;
	}
}
