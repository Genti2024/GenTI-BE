package com.gt.genti.dto.admin.request;

import com.gt.genti.domain.enums.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "유저 권한 수정 요청 Dto")
public class UserRoleUpdateRequestDto {
	@NotNull
	@Schema(description = "유저의 권한")
	UserRole userRole;

	@Builder
	public UserRoleUpdateRequestDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
