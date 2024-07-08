package com.gt.genti.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.dto.request.UserInfoUpdateRequestDto;
import com.gt.genti.user.dto.response.UserFindResponseDto;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserController] 유저 컨트롤러", description = "유저의 정보를 조회,수정합니다.")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "내정보보기", description = "유저의 정보를 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)

	})
	@GetMapping("/v1")
	public ResponseEntity<ApiResult<UserFindResponseDto>> getUserInfo(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.getUserInfo(userId));
	}

	@Operation(summary = "내정보 수정", description = "유저의 정보를 수정합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)

	})
	@PutMapping("/v1")
	public ResponseEntity<ApiResult<UserFindResponseDto>> updateUserInfo(
		@AuthUser Long userId,
		@RequestBody @Valid UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return GentiResponse.success(userService.updateUserInfo(userId, userInfoUpdateRequestDto));
	}

	@Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	@DeleteMapping("/v1")
	public ResponseEntity<ApiResult<Boolean>> deleteUserSoft(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.deleteUserSoft(userId));
	}

	@Operation(summary = "회원 복구", description = "회원탈퇴 취소 처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound),
		@EnumResponse(ResponseCode.CannotRestoreUser)

	})
	@PutMapping("/v1/restore")
	public ResponseEntity<ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.restoreSoftDeletedUser(userId));
	}

	@Operation(summary = "내 사진 전체조회", description = "내가 사진생성요청으로 생성된 사진 전체 조회 Pagination")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	@GetMapping("/v1/pictures/my")
	public ResponseEntity<ApiResult<Page<CommonPictureResponseDto>>> getAllMyGeneratedPicture(
		@AuthUser Long userId,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"id", "createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return GentiResponse.success(userService.getAllMyGeneratedPicture(userId, pageable));
	}
}
