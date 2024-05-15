package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.CreatorInfoResponseDto;
import com.gt.genti.dto.UpdateAccountInfoRequestDto;
import com.gt.genti.dto.UpdateCreatorStatusRequestDto;
import com.gt.genti.dto.UpdateCreatorStatusResponseDto;
import com.gt.genti.dto.UserInfoResponseDto;
import com.gt.genti.other.annotation.ToBeUpdated;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.CreatorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {
	private final CreatorService creatorService;

	@GetMapping("")
	public ResponseEntity<ApiResult<CreatorInfoResponseDto>> getCreatorInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(creatorService.getCreatorInfo(userDetails.getId()));
	}

	@PostMapping("/account")
	public ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UpdateAccountInfoRequestDto updateAccountInfoRequestDto) {
		return success(creatorService.updateAccountInfo(userDetails.getId(), updateAccountInfoRequestDto));
	}
	@PostMapping("/status")
	public ResponseEntity<ApiResult<UpdateCreatorStatusResponseDto>> updateCreatorStatus(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UpdateCreatorStatusRequestDto updateCreatorStatusRequestDto) {
		return success(creatorService.updateCreatorStatus(userDetails.getId(), updateCreatorStatusRequestDto));
	}
}
