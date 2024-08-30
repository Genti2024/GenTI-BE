package com.gt.genti.settlement.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.settlement.api.SettlementApi;
import com.gt.genti.settlement.service.SettlementService;
import com.gt.genti.settlementandcashout.dto.response.SettlementAndCashoutPageResponseDto;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/creators/settlements")
@RequiredArgsConstructor
public class SettlementController implements SettlementApi {
	private final SettlementService settlementService;

	@Logging(item = LogItem.SETTLEMENT, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@GetMapping
	public ResponseEntity<ApiResult<SettlementAndCashoutPageResponseDto>> getMySettlements(
		@AuthUser Long userId,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"id", "createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(settlementService.getAllSettlements(userId, pageable));
	}

}