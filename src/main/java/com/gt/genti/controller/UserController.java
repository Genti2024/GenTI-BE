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

import com.gt.genti.domain.User;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.dto.UserInfoUpdateRequestDto;
import com.gt.genti.service.UserService;
import com.gt.genti.util.ApiUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> getUserInfo(@AuthenticationPrincipal User user) {
		return success(userService.getUserInfo(user.getId()));
	}

	@PutMapping("")
	public ResponseEntity<ApiUtils.ApiResult<UserInfoResponseDto>> updateUserInfo(@AuthenticationPrincipal User user,
		@RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return success(userService.updateUserInfo(user.getId(), userInfoUpdateRequestDto));
	}

	@DeleteMapping("")
	public ResponseEntity<ApiUtils.ApiResult<Boolean>> deleteUserInfoSoft(@AuthenticationPrincipal User user) {
		return success(userService.deleteUserInfoSoft(user.getId()));
	}

}
