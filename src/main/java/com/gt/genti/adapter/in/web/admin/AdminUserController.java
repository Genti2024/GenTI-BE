package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.UserService;
import com.gt.genti.domain.enums.UserRole;
import com.gt.genti.domain.enums.converter.EnumUtil;
import com.gt.genti.dto.admin.UserRoleUpdateRequestDto;
import com.gt.genti.dto.admin.UserStatusUpdateRequestDto;
import com.gt.genti.dto.admin.UserFindByAdminResponseDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
	private final UserService userService;

	@PostMapping("/users/{userId}/status")
	public ResponseEntity<ApiResult<Boolean>> changeUserStatus(@PathVariable Long userId,
		@RequestBody @Valid UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		return success(userService.updateUserStatus(userId, userStatusUpdateRequestDto));
	}

	@PostMapping("/users/{userId}/role")
	public ResponseEntity<ApiResult<Boolean>> changeUserRole(@PathVariable Long userId,
		@RequestBody @Valid UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		return success(userService.updateUserRole(userId, userRoleUpdateRequestDto));
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResult<Page<UserFindByAdminResponseDto>>> getAllUserInfo(
		@RequestParam(value = "role", defaultValue = "ALL") @NotNull String role,
		@RequestParam(value = "page", defaultValue = "0") @NotNull int page,
		@RequestParam(value = "size", defaultValue = "10") @NotNull @Min(1) int size
	) {
		//TODO Role에 따라 분기
		// edited at 2024-05-24
		// author 서병렬
		Page<UserFindByAdminResponseDto> result;
		if (Objects.equals(role, "ALL")) {
			result = userService.getAllUserInfo(page,size);
		} else {
			UserRole userRole = EnumUtil.stringToEnum(UserRole.class, role);
			result = userService.getAllUserInfo(userRole, page,size);
		}
		return success(result);
	}

}
