package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.error.ResponseCode.*;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.DepositNotFound)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<SettlementAndWithdrawPageResponseDto>> getMySettlements(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"id", "createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(settlementService.getAllSettlements(userDetails.getUser(), pageable));
	}

}