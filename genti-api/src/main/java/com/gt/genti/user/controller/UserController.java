package com.gt.genti.user.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import com.gt.genti.user.dto.response.SignUpResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.auth.dto.request.SignUpRequestDTO;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.user.api.UserApi;
import com.gt.genti.user.dto.request.UserInfoUpdateRequestDto;
import com.gt.genti.user.dto.response.UserFindResponseDto;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.user.service.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApi {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<ApiResult<UserFindResponseDto>> getUserInfo(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.getUserInfo(userId));
	}

	@PutMapping
	public ResponseEntity<ApiResult<UserFindResponseDto>> updateUserInfo(
		@AuthUser Long userId,
		@RequestBody @Valid UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return GentiResponse.success(userService.updateUserInfo(userId, userInfoUpdateRequestDto));
	}

	@PostMapping("/signup")
	@Logging(item = LogItem.USER, action = LogAction.SIGNUP, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<SignUpResponseDTO>> signUp(
		@AuthUser Long userId,
		@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
		return success(userService.signUp(userId, signUpRequestDTO));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResult<Boolean>> logout(@AuthUser Long userId) {
		return success(userService.logout(userId));
	}

	@DeleteMapping
	@Logging(item = LogItem.USER, action = LogAction.DELETE, requester = LogRequester.ANONYMOUS)
	public ResponseEntity<ApiResult<Boolean>> deleteUserHard(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.deleteUserHard(userId));
	}

	@Deprecated
	@PutMapping("/restore")
	public ResponseEntity<ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthUser Long userId) {
		return GentiResponse.success(userService.restoreSoftDeletedUser(userId));
	}

	@GetMapping("/pictures/my")
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

	@GetMapping("/pictures")
	public ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> getAllMyGeneratedPictureNoPage(
			@AuthUser Long userId
	) {
		return GentiResponse.success(userService.getAllMyGeneratedPictureNoPage(userId));
	}
}
