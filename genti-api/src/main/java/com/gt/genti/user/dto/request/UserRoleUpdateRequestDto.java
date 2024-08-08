package com.gt.genti.user.dto.request;

import com.gt.genti.user.model.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "[User][Admin] 유저 권한 수정 요청 Dto", description = "사용자 권한을 수정함, 주로 사용자->공급자 변경시 사용될 예정")
public class UserRoleUpdateRequestDto {
	@NotNull
	@Schema(description = "유저의 권한")
	UserRole userRole;

	@Builder
	public UserRoleUpdateRequestDto(UserRole userRole) {
		this.userRole = userRole;
	}
}
