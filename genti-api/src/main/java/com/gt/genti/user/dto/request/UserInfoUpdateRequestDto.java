package com.gt.genti.user.dto.request;

import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "[User][User] 사용자 정보 수정 요청 dto", description = "사용자 정보를 수정함")
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
