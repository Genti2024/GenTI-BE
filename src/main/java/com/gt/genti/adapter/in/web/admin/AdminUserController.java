package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.gt.genti.dto.admin.request.UserRoleUpdateRequestDto;
import com.gt.genti.dto.admin.request.UserStatusUpdateRequestDto;
import com.gt.genti.dto.admin.response.UserFindByAdminResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.valid.ValidEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminUserController] 어드민 유저 컨트롤러", description = "유저의 활성상태,권한 등을 수정, 조회합니다.")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
	private final UserService userService;

	@Operation(summary = "유저 활성화/비활성화", description = "유저의 활성상태(활성화/비활성화)를 변경")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/users/{userId}/status")
	public ResponseEntity<ApiResult<Boolean>> changeUserStatus(@PathVariable Long userId,
		@RequestBody @Valid UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
		return success(userService.updateUserStatus(userId, userStatusUpdateRequestDto));
	}

	@Operation(summary = "권한 수정", description = "유저의 권한을 변경")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/users/{userId}/role")
	public ResponseEntity<ApiResult<Boolean>> changeUserRole(@PathVariable Long userId,
		@RequestBody @Valid UserRoleUpdateRequestDto userRoleUpdateRequestDto) {
		return success(userService.updateUserRole(userId, userRoleUpdateRequestDto));
	}

	@Operation(summary = "유저정보 전체조회", description = "유저 전체조회 페이지네이션")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/users")
	public ResponseEntity<ApiResult<Page<UserFindByAdminResponseDto>>> getAllUserInfo(
		@RequestParam(value = "role") @ValidEnum(value = UserRole.class, hasAllOption = true) @NotNull String role,
		@RequestParam(value = "page") @NotNull @Min(0) int page,
		@RequestParam(value = "size") @NotNull @Min(1) int size
	) {
		Pageable pageable = PageRequest.of(page, size);

		return success(userService.getAllUserInfo(pageable, role));
	}

}
