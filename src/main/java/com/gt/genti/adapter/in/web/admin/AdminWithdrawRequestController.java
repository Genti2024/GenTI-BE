package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.admin.response.WithdrawCompletionResponseDto;
import com.gt.genti.dto.admin.response.WithdrawFindResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.valid.ValidWithdrawStatus;
import com.gt.genti.service.WithdrawService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/withdraw-requests")
@RequiredArgsConstructor
public class AdminWithdrawRequestController {
	private final WithdrawService withDrawService;

	@GetMapping("")
	public ResponseEntity<ApiResult<Page<WithdrawFindResponseDto>>> getAllWithdrawList(
		@RequestParam(name = "page") @NotNull @Min(0) int page,
		@RequestParam(name = "size") @NotNull @Min(1) int size,
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@RequestParam(name = "status", defaultValue = "ALL") @ValidWithdrawStatus String status
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

		return success(withDrawService.getAllWithdrawRequests(pageable, status));
	}

	@PostMapping("{withdrawRequestId}")
	public ResponseEntity<ApiResult<WithdrawCompletionResponseDto>> complete(
		@PathVariable(name = "withdrawRequestId") Long withdrawRequestId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(withDrawService.complete(withdrawRequestId, userDetails.getUser()));
	}
}
