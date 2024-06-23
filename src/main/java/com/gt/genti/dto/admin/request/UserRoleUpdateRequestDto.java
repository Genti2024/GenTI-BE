package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "ㅁㄴㅇㄹ")

public class UserRoleUpdateRequestDto {
	@NotNull
	@Schema(name = "userRole")
	UserRole userRole;

	@Builder
	public UserRoleUpdateRequestDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
