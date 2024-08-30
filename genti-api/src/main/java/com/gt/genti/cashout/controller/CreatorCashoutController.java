package com.gt.genti.cashout.controller;

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

import com.gt.genti.cashout.api.CreatorCashoutApi;
import com.gt.genti.cashout.dto.response.CashoutFindByCreatorResponseDto;
import com.gt.genti.cashout.service.CashoutService;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/creators/cashouts")
@RequiredArgsConstructor
public class CreatorCashoutController implements CreatorCashoutApi {
	private final CashoutService cashoutService;

	@Logging(item = LogItem.CASHOUT, action = LogAction.CREATE, requester = LogRequester.CREATOR)
	@PostMapping
	public ResponseEntity<ApiResult<CashoutFindByCreatorResponseDto>> createCashout(@AuthUser Long userId) {

		return success(cashoutService.create(userId));
	}

	@Logging(item = LogItem.CASHOUT, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@GetMapping
	public ResponseEntity<ApiResult<Page<CashoutFindByCreatorResponseDto>>> getCashout(@AuthUser Long userId,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true) @RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true) @RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"})) @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"})) @RequestParam(name = "direction", defaultValue = "desc") String direction) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(cashoutService.findCashoutList(userId, pageable));
	}
}
