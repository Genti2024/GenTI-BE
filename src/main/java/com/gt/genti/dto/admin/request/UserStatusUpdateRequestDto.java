package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "유저 상태(활성화/비활성화) 수정 요청 dto")
public class UserStatusUpdateRequestDto {
	@NotNull
	@Schema(description = "유저의 상태")
	UserStatus userStatus;
}
