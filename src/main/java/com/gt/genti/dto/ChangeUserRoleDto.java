package com.gt.genti.dto;

import com.gt.genti.domain.enums.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeUserRoleDto {
	UserRole userRole;
}
