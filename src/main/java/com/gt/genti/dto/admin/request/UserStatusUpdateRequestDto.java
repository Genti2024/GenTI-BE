package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class UserStatusUpdateRequestDto {
	@NotNull
	@Schema(name = "userStatus")
	UserStatus userStatus;
}
