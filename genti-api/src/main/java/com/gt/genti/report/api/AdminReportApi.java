package com.gt.genti.report.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.report.dto.request.ReportUpdateRequestDto;
import com.gt.genti.report.dto.response.ReportFindByAdminResponseDto;
import com.gt.genti.report.model.ReportStatus;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.validator.ValidEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AuthorizedAdmin
@Tag(name = "[AdminReportController] 어드민 신고 컨트롤러", description = "유저의 신고내역을 조회,수정합니다.")
public interface AdminReportApi {
	@Operation(summary = "신고내역 전체조회", description = "전체 신고내역을 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Page<ReportFindByAdminResponseDto>>> getAllReports(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "신고의 상태 ALL : 모든 상태 조회 NOT_RESOLVED : 해결 전 RESOLVED : 해결 완료", example = "ADMIN_IN_PROGRESS", schema = @Schema(
			allowableValues = {"NOT_RESOLVED", "RESOLVED", "ALL"}))
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = ReportStatus.class, hasAllOption = true) String status
	);

	@Operation(summary = "특정 사용자의 신고내역 조회", description = "사용자의 email을 전달 받아 해당 사용자의 전체 신고내역을 페이지네이션 조회")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFoundByEmail)
	})
	ResponseEntity<ApiResult<Page<ReportFindByAdminResponseDto>>> getReportsByUserEmail(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "신고의 상태 ALL : 모든 상태 조회 NOT_RESOLVED : 해결 전 RESOLVED : 해결 완료", example = "ADMIN_IN_PROGRESS", schema = @Schema(
			allowableValues = {"NOT_RESOLVED", "RESOLVED", "ALL"}))
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = ReportStatus.class, hasAllOption = true) String status,
		@Parameter(description = "사용자 이메일", example = "example@naver.com", required = true)
		@PathVariable("email") @NotNull String email
	);

	@Operation(summary = "신고 상태 수정", description = "신고의 상태(어드민 처리 여부)를 변경합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Boolean>> updateReport(
		@RequestBody @Valid ReportUpdateRequestDto reportUpdateRequestDto);
}
