package com.gt.genti.withdraw.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.validator.ValidEnum;
import com.gt.genti.withdraw.dto.response.WithdrawCompletionResponseDto;
import com.gt.genti.withdraw.dto.response.WithdrawFindByAdminResponseDto;
import com.gt.genti.withdraw.service.WithdrawService;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/withdraw-requests")
@RequiredArgsConstructor
public class AdminWithdrawRequestController implements com.gt.genti.withdraw.api.AdminWithdrawRequestApi {
	private final WithdrawService withDrawService;

	@Logging(item = LogItem.CASHOUT, action = LogAction.VIEW, requester = LogRequester.ADMIN)
	@GetMapping
	public ResponseEntity<ApiResult<Page<WithdrawFindByAdminResponseDto>>> getAllWithdrawList(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(0) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "출금요청 상태, ALL : 모든 상태", example = "IN_PROGRESS", schema = @Schema(
			allowableValues = {"IN_PROGRESS", "COMPLETED", "REJECTED", "ALL"}))
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = WithdrawRequestStatus.class, hasAllOption = true) String status
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return success(withDrawService.getAllWithdrawRequests(pageable, status));
	}

	@Logging(item = LogItem.CASHOUT, action = LogAction.VIEW, requester = LogRequester.ADMIN)
	@GetMapping("/{email}")
	public ResponseEntity<ApiResult<Page<WithdrawFindByAdminResponseDto>>> getWithdrawListByCreatorEmail(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(0) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "출금요청 상태, ALL : 모든 상태", example = "IN_PROGRESS", schema = @Schema(
			allowableValues = {"IN_PROGRESS", "COMPLETED", "REJECTED", "ALL"}))
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = WithdrawRequestStatus.class, hasAllOption = true) String status,
		@Parameter(description = "사용자 이메일", example = "example@naver.com", required = true)
		@PathVariable("email") @NotNull String email
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return success(withDrawService.getWithdrawListByCreatorEmail(email, pageable, status));
	}

	@Logging(item = LogItem.CASHOUT, action = LogAction.COMPLETE, requester = LogRequester.ADMIN)
	@PostMapping("/{withdrawRequestId}")
	public ResponseEntity<ApiResult<WithdrawCompletionResponseDto>> complete(
		@Parameter(description = "출금요청 Id", example = "1")
		@PathVariable(value = "withdrawRequestId") Long withdrawRequestId,
		@AuthUser Long userId
	) {
		return success(withDrawService.complete(withdrawRequestId, userId));
	}
}
