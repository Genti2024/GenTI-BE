package com.gt.genti.withdraw.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.withdraw.dto.response.WithdrawFindByCreatorResponseDto;
import com.gt.genti.withdraw.service.WithdrawService;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorWithdrawController] 공급자 출금요청 컨트롤러", description = "공급자가 출금요청을 수행합니다.")
@RestController
@RequestMapping("/api/v1/creators/withdraw")
@RequiredArgsConstructor
public class CreatorWithdrawController {
	private final WithdrawService withDrawService;

	@Logging(item = LogItem.CASHOUT, action = LogAction.CREATE, requester = LogRequester.CREATOR)
	@Operation(summary = "출금요청", description = "공급자가 작업한 정산결과를 바탕으로 출금요청을 생성합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.CannotCreateWithdrawalDueToSettlementsNotAvailable)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<WithdrawFindByCreatorResponseDto>> createWithdrawRequest(
		@AuthUser Long userId
	) {

		return success(withDrawService.create(userId));
	}

	@Logging(item = LogItem.CASHOUT, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@Operation(summary = "출금요청내역조회", description = "공급자의 모든 출금요청을 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<Page<WithdrawFindByCreatorResponseDto>>> getWithdrawRequest(
		@AuthUser Long userId,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(withDrawService.findWithdrawList(userId, pageable));
	}
}
