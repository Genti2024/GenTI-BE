package com.gt.genti.withdraw.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.validator.ValidEnum;
import com.gt.genti.withdraw.dto.response.WithdrawCompletionResponseDto;
import com.gt.genti.withdraw.dto.response.WithdrawFindByAdminResponseDto;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedAdmin
@Tag(name = "[AdminWithdrawRequestController] 어드민 출금요청 컨트롤러", description = "공급자의 출금 요청을 조회,수정합니다.")
public interface AdminWithdrawRequestApi {
	@Operation(summary = "출금요청 전체조회", description = "출금요청 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Page<WithdrawFindByAdminResponseDto>>> getAllWithdrawList(
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
	);

	@Operation(summary = "특정 공급자의 출금요청 조회", description = "공급자의 email을 전달 받아 해당 공급자의 전체 출금요청을 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFoundByEmail)
	})
	ResponseEntity<ApiResult<Page<WithdrawFindByAdminResponseDto>>> getWithdrawListByCreatorEmail(
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
	);

	@Operation(summary = "출금요청 완료처리", description = "송금 완료 후 출금요청 완료처리")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound),
		@EnumResponse(ResponseCode.DepositNotFound)
	})
	ResponseEntity<ApiResult<WithdrawCompletionResponseDto>> complete(
		@Parameter(description = "출금요청 Id", example = "1")
		@PathVariable(value = "withdrawRequestId") Long withdrawRequestId,
		@AuthUser Long userId
	);
}
