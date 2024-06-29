package com.gt.genti.creator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.creator.dto.request.AccountUpdateRequestDto;
import com.gt.genti.creator.dto.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.creator.dto.response.CreatorFindResponseDto;
import com.gt.genti.creator.dto.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.creator.service.CreatorService;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorController] 공급자 정보 컨트롤러", description = "공급자의 정보를 조회,수정합니다.")
@RestController
@RequestMapping("/api/v1/creators")
@RequiredArgsConstructor
public class CreatorController {
	private final CreatorService creatorService;

	@Operation(summary = "공급자 내정보 보기", description = "공급자의 내정보 보기")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)

	})
	@GetMapping("")
	public ResponseEntity<ApiResult<CreatorFindResponseDto>> getCreatorInfo(
		@AuthUser Long userId) {
		return GentiResponse.success(creatorService.getCreatorInfo(userId));
	}

	@Operation(summary = "공급자 계좌정보 수정", description = "공급자의 내 계좌정보 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)
	})
	@PostMapping("/account")
	public ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthUser Long userId,
		@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto) {
		return GentiResponse.success(creatorService.updateAccountInfo(userId, accountUpdateRequestDto));
	}

	@Operation(summary = "공급자 작업가능상태 수정", description = "공급자의 내 작업가능상태 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)
	})
	@PostMapping("/status")
	public ResponseEntity<ApiResult<CreatorStatusUpdateResponseDto>> updateCreatorStatus(
		@AuthUser Long userId,
		@RequestBody @Valid CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		return GentiResponse.success(creatorService.updateCreatorStatus(userId, creatorStatusUpdateRequestDto));
	}
}
