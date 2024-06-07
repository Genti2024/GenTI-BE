package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.creator.CreatorFindResponseDto;
import com.gt.genti.dto.creator.AccountUpdateRequestDto;
import com.gt.genti.dto.creator.CreatorStatusUpdateRequestDto;
import com.gt.genti.dto.creator.CreatorStatusUpdateResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.CreatorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {
	private final CreatorService creatorService;

	@GetMapping("")
	public ResponseEntity<ApiResult<CreatorFindResponseDto>> getCreatorInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(creatorService.getCreatorInfo(userDetails.getId()));
	}

	@PostMapping("/account")
	public ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto) {
		return success(creatorService.updateAccountInfo(userDetails.getId(), accountUpdateRequestDto));
	}
	@PostMapping("/status")
	public ResponseEntity<ApiResult<CreatorStatusUpdateResponseDto>> updateCreatorStatus(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		return success(creatorService.updateCreatorStatus(userDetails.getId(), creatorStatusUpdateRequestDto));
	}


}
