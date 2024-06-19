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

import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.dto.user.response.UserFindResponseDto;
import com.gt.genti.dto.user.response.UserInfoUpdateResponseDto;
import com.gt.genti.other.annotation.ToBeUpdated;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.dto.user.request.UserInfoUpdateRequestDto;
import com.gt.genti.application.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@ToBeUpdated
	@CheckUserIsQuit
	@GetMapping("")
	public ResponseEntity<ApiResult<UserFindResponseDto>> getUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.getUserInfo(userDetails.getUser()));
	}

	@ToBeUpdated
	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiResult<UserInfoUpdateResponseDto>> updateUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
		return success(userService.updateUserInfo(userDetails.getUser(), userInfoUpdateRequestDto));
	}


	@CheckUserIsQuit
	@DeleteMapping("")
	public ResponseEntity<ApiResult<Boolean>> deleteUserSoft(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.deleteUserInfoSoft(userDetails.getUser()));
	}

	@PutMapping("/restore")
	public ResponseEntity<ApiResult<Boolean>> restoreSoftDeletedUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(userService.restoreSoftDeletedUser(userDetails.getUser()));
	}

	@GetMapping("/pictures/my")
	public ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> getAllMyGeneratedPicture(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	){
		return success(userService.getAllMyGeneratedPicture(userDetails.getUser()));
	}
}
