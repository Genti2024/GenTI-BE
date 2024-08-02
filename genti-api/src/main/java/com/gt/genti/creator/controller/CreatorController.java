package com.gt.genti.creator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.creator.api.CreatorApi;
import com.gt.genti.creator.dto.request.AccountUpdateRequestDto;
import com.gt.genti.creator.dto.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.creator.dto.response.CreatorFindResponseDto;
import com.gt.genti.creator.dto.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.creator.service.CreatorService;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.user.model.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/creators")
@RequiredArgsConstructor
public class CreatorController implements CreatorApi {
	private final CreatorService creatorService;

	@Logging(item = LogItem.CREATOR_MY, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@GetMapping
	public ResponseEntity<ApiResult<CreatorFindResponseDto>> getCreatorInfo(
		@AuthUser Long userId) {
		return GentiResponse.success(creatorService.getCreatorInfo(userId));
	}

	@Logging(item = LogItem.CREATOR_ACCOUNT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/account")
	public ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthUser Long userId,
		@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto) {
		return GentiResponse.success(creatorService.updateAccountInfo(userId, accountUpdateRequestDto));
	}

	@Logging(item = LogItem.CREATOR_STATUS, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/status")
	public ResponseEntity<ApiResult<CreatorStatusUpdateResponseDto>> updateCreatorStatus(
		@AuthUser Long userId,
		@RequestBody @Valid CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		return GentiResponse.success(creatorService.updateCreatorStatus(userId, creatorStatusUpdateRequestDto));
	}
}
