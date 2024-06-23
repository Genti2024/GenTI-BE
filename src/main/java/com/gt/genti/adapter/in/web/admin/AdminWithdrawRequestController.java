package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.domain.enums.WithdrawRequestStatus;
import com.gt.genti.dto.admin.response.WithdrawCompletionResponseDto;
import com.gt.genti.dto.admin.response.WithdrawFindResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.valid.ValidEnum;
import com.gt.genti.service.WithdrawService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminWithdrawRequestController] 어드민 출금요청 컨트롤러", description = "공급자의 출금 요청을 조회,수정합니다.")
@RestController
@RequestMapping("/api/admin/withdraw-requests")
@RequiredArgsConstructor
public class AdminWithdrawRequestController {
	private final WithdrawService withDrawService;

	@Operation(summary = "출금요청 전체조회", description = "출금요청 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<Page<WithdrawFindResponseDto>>> getAllWithdrawList(
		@RequestParam(name = "page") @NotNull @Min(0) int page,
		@RequestParam(name = "size") @NotNull @Min(1) int size,
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = WithdrawRequestStatus.class, hasAllOption = true) String status
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return success(withDrawService.getAllWithdrawRequests(pageable, status));
	}

	@Operation(summary = "출금요청 완료처리", description = "송금 완료 후 출금요청 완료처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("{withdrawRequestId}")
	public ResponseEntity<ApiResult<WithdrawCompletionResponseDto>> complete(
		@PathVariable(name = "withdrawRequestId") Long withdrawRequestId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(withDrawService.complete(withdrawRequestId, userDetails.getUser()));
	}
}
