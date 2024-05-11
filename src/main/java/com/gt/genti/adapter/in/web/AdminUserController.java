package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.application.service.UserService;
import com.gt.genti.dto.ChangeUserStatusRequestDto;
import com.gt.genti.dto.ReportResponseDto;
import com.gt.genti.dto.ReportUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
	UserService userService;
	@PostMapping("/users/{userId}")
	public ResponseEntity<ApiResult<Boolean>> changeUserStatus(@PathVariable Long userId, @RequestBody ChangeUserStatusRequestDto changeUserStatusRequestDto){
		return success(userService.updateUserStatus(userId, changeUserStatusRequestDto));
	}


}
