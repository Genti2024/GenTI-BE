package com.gt.genti.user.dto.request;

import com.gt.genti.user.model.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[User][Admin] 유저 상태(활성화/비활성화) 수정 요청 dto", description = "유저 비활성화시 사용")
public class UserStatusUpdateRequestDto {
	@NotNull
	@Schema(description = "유저의 상태")
	UserStatus userStatus;
}
