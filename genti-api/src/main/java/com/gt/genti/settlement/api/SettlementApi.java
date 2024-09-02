package com.gt.genti.settlement.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.settlementandcashout.dto.response.SettlementAndCashoutPageResponseDto;
import com.gt.genti.swagger.AuthorizedCreator;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedCreator
@Tag(name = "[SettlementController] 공급자 정산내역 컨트롤러", description = "공급자의 정산&출금내역을 조회합니다.")
public interface SettlementApi {

	@Operation(summary = "정산&출금내역 조회", description = "공급자의 정산&출금내역을 페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.DepositNotFound)
	})
	ResponseEntity<ApiResult<SettlementAndCashoutPageResponseDto>> getMySettlements(
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
	);
}
