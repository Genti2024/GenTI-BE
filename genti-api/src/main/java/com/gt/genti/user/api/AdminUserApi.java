package com.gt.genti.user.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.dto.request.UserRoleUpdateRequestDto;
import com.gt.genti.user.dto.request.UserStatusUpdateRequestDto;
import com.gt.genti.user.dto.response.UserFindByAdminResponseDto;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.validator.ValidEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedAdmin
@Tag(name = "[AdminUserController] 어드민 유저 컨트롤러", description = "유저의 활성상태,권한 등을 수정, 조회합니다.")
public interface AdminUserApi {

	@Operation(summary = "유저 활성화/비활성화", description = "유저의 활성상태(활성화/비활성화)를 변경")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Boolean>> changeUserStatus(
		@PathVariable(value = "userId") Long userId,
		@RequestBody @Valid UserStatusUpdateRequestDto userStatusUpdateRequestDto);

	@Operation(summary = "권한 수정", description = "유저의 권한을 변경")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Boolean>> changeUserRole(
		@PathVariable(value = "userId") Long userId,
		@RequestBody @Valid UserRoleUpdateRequestDto userRoleUpdateRequestDto);

	@Operation(summary = "유저정보 전체조회", description = "유저 전체조회 페이지네이션")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Page<UserFindByAdminResponseDto>>> getAllUserInfo(
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
	);
}
