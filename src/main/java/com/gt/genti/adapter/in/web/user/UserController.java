package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.UserService;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.dto.user.request.UserInfoUpdateRequestDto;
import com.gt.genti.dto.user.response.UserFindResponseDto;
import com.gt.genti.dto.user.response.UserInfoUpdateResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.annotation.ToBeUpdated;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserController] 유저 컨트롤러", description = "유저의 정보를 조회,수정합니다.")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "내정보보기", description = "유저의 정보를 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@ToBeUpdated
	@GetMapping("")
	public ResponseEntity<ApiResult<UserFindResponseDto>> getUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.getUserInfo(userDetails.getUser()));
	}

	@Operation(summary = "내정보 수정", description = "유저의 정보를 수정합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@ToBeUpdated
	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiResult<UserInfoUpdateResponseDto>> updateUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return success(userService.updateUserInfo(userDetails.getUser(), userInfoUpdateRequestDto));
	}

	@Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@CheckUserIsQuit
	@DeleteMapping("")
	public ResponseEntity<ApiResult<Boolean>> deleteUserSoft(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.deleteUserInfoSoft(userDetails.getUser()));
	}

	@Operation(summary = "회원 복구", description = "회원탈퇴 취소 처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PutMapping("/restore")
	public ResponseEntity<ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.restoreSoftDeletedUser(userDetails.getUser()));
	}

	@Operation(summary = "내 사진 전체조회", description = "내가 사진생성요청으로 생성된 사진 전체 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/pictures/my")
	public ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> getAllMyGeneratedPicture(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(userService.getAllMyGeneratedPicture(userDetails.getUser()));
	}
}
