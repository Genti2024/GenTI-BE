package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.creator.response.CreatorFindResponseDto;
import com.gt.genti.dto.creator.request.AccountUpdateRequestDto;
import com.gt.genti.dto.creator.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.dto.creator.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.service.CreatorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorController] 공급자 정보 컨트롤러", description = "공급자의 정보를 조회,수정합니다.")
@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {
	private final CreatorService creatorService;

	@Operation(summary = "공급자 내정보 보기", description = "공급자의 내정보 보기")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<CreatorFindResponseDto>> getCreatorInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(creatorService.getCreatorInfo(userDetails.getUser()));
	}

	@Operation(summary = "공급자 계좌정보 수정", description = "공급자의 내 계좌정보 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/account")
	public ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto) {
		return success(creatorService.updateAccountInfo(userDetails.getUser(), accountUpdateRequestDto));
	}

	@Operation(summary = "공급자 작업가능상태 수정", description = "공급자의 내 작업가능상태 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/status")
	public ResponseEntity<ApiResult<CreatorStatusUpdateResponseDto>> updateCreatorStatus(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto) {
		return success(creatorService.updateCreatorStatus(userDetails.getUser(), creatorStatusUpdateRequestDto));
	}
}
