package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoleUpdateRequestDto {
	@NotNull
	UserRole userRole;

	@Builder
	public UserRoleUpdateRequestDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
