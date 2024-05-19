package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.UserService;
import com.gt.genti.dto.ChangeUserRoleDto;
import com.gt.genti.dto.ChangeUserStatusDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
	UserService userService;
	@PostMapping("/users/{userId}/status")
	public ResponseEntity<ApiResult<Boolean>> changeUserStatus(@PathVariable Long userId, @RequestBody ChangeUserStatusDto changeUserStatusDto){
		return success(userService.updateUserStatus(userId, changeUserStatusDto));
	}

	@PostMapping("/users/{userId}/role")
	public ResponseEntity<ApiResult<Boolean>> changeUserRole(@PathVariable Long userId, @RequestBody ChangeUserRoleDto changeUserRoleDto){
		return success(userService.updateUserRole(userId, changeUserRoleDto));
	}

}
