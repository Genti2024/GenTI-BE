package com.gt.genti.dto;

import com.gt.genti.domain.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeUserRoleDto {
	UserRole userRole;

	@Builder
	public ChangeUserRoleDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
