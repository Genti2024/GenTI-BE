package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.aop.annotation.CheckUserIsQuit;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.security.PrincipalDetail;
import com.gt.genti.service.UserService;
import com.gt.genti.util.ApiUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@CheckUserIsQuit
	@GetMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> getUserInfo(
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return success(userService.getUserInfo(principalDetail.getUser().getId()));
	}

	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> updateUserInfo(
		@AuthenticationPrincipal PrincipalDetail principalDetail,
		@RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return success(userService.updateUserInfo(principalDetail.getUser().getId(), userInfoUpdateRequestDto));
	}

	@CheckUserIsQuit
	@DeleteMapping("")
	public ResponseEntity<ApiUtils.ApiResult<Boolean>> deleteUserSoft(
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return success(userService.deleteUserInfoSoft(principalDetail.getUser().getId()));
	}

	@PutMapping("/restore")
	public ResponseEntity<ApiUtils.ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return success(userService.restoreSoftDeletedUser(principalDetail.getUser().getId()));
	}

}
