package com.gt.genti.dto;

import com.gt.genti.domain.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRoleUpdateRequestDto {
	UserRole userRole;

	@Builder
	public UserRoleUpdateRequestDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
