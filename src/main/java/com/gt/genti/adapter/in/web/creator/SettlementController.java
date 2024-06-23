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
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.service.SettlementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[SettlementController] 공급자 정산내역 컨트롤러", description = "공급자의 정산&출금내역을 조회합니다.")
@RestController
@RequestMapping("/api/creators/settlements")
@RequiredArgsConstructor
public class SettlementController {
	private final SettlementService settlementService;

	@Operation(summary = "정산&출금내역 조회", description = "공급자의 정산&출금내역을 페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<SettlementAndWithdrawPageResponseDto>> getMySettlements(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestParam(name = "page") @NotNull @Min(0) int page,
		@RequestParam(name = "size") @NotNull @Min(1) int size,
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(settlementService.getAllSettlements(userDetails.getUser(), pageable));
	}

}