package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStatusUpdateRequestDto {
	@NotNull
	UserStatus userStatus;
}
