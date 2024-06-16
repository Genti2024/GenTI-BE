package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.creator.response.SettlementAndWithdrawPageResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.SettlementService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators/settlements")
@RequiredArgsConstructor
public class SettlementController {
	private final SettlementService settlementService;

	@GetMapping("")
	public ResponseEntity<ApiResult<SettlementAndWithdrawPageResponseDto>> getMySettlements(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam @NotNull @Min(0) int page,
		@RequestParam @NotNull @Min(1) int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(settlementService.getAllSettlements(userDetails.getUser(), pageable));
	}

}