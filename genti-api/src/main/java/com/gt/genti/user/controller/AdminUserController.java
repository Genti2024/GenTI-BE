package com.gt.genti.user.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.user.api.AdminUserApi;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.dto.request.UserStatusUpdateRequestDto;
import com.gt.genti.user.dto.response.UserFindByAdminResponseDto;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.service.UserService;
import com.gt.genti.validator.ValidEnum;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminUserController implements AdminUserApi {
	private final UserService userService;

	@PostMapping("/users/{userId}/status")
	public ResponseEntity<ApiResult<Boolean>> changeUserStatus(
		@PathVariable(value = "userId") Long userId,
		@RequestBody @Valid UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		return success(userService.updateUserStatus(userId, userStatusUpdateRequestDto));
	}

	@PostMapping("/users/{userId}/role")
	public ResponseEntity<ApiResult<Boolean>> changeUserRole(
		@PathVariable(value = "userId") Long userId,
		@RequestBody @Valid UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		return success(userService.updateUserRole(userId, userRoleUpdateRequestDto));
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResult<Page<UserFindByAdminResponseDto>>> getAllUserInfo(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "유저의 권한", example = "USER", schema = @Schema(
			allowableValues = {"USER", "CREATOR", "ADMIN", "ALL"}))
		@RequestParam(name = "role", defaultValue = "ALL") @ValidEnum(value = UserRole.class, hasAllOption = true) String role,
		@Parameter(description = "유저의 email")
		@RequestParam(name = "email", required = false) @Email(message = "올바른 email 형식이 아닙니다.") String email
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		if (email != null) {
			return success(userService.getUserInfoByEmail(email));
		}

		if ("ALL".equals(role)) {
			return success(userService.getAllUserInfo(pageable));
		} else {
			return success(userService.getAllUserInfoByUserRole(UserRole.valueOf(role), pageable));
		}

	}

}
