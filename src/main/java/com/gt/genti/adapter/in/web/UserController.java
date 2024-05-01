package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.config.auth.UserDetailsImpl;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.application.service.UserService;
import com.gt.genti.other.util.ApiUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@CheckUserIsQuit
	@GetMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> getUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.getUserInfo(userDetails.getId()));
	}

	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> updateUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return success(userService.updateUserInfo(userDetails.getId(), userInfoUpdateRequestDto));
	}

	@CheckUserIsQuit
	@DeleteMapping("")
	public ResponseEntity<ApiUtils.ApiResult<Boolean>> deleteUserSoft(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.deleteUserInfoSoft(userDetails.getId()));
	}

	@PutMapping("/restore")
	public ResponseEntity<ApiUtils.ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.restoreSoftDeletedUser(userDetails.getId()));
	}

}
