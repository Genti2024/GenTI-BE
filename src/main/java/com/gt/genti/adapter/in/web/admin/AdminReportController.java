package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.dto.admin.response.ReportFindResponseDto;
import com.gt.genti.dto.admin.request.ReportUpdateRequestDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminReportController] 어드민 신고 컨트롤러", description = "유저의 신고내역을 조회,수정합니다.")
@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {
	private final ReportService reportService;

	@Operation(summary = "신고내역 전체조회", description = "전체 신고내역을 조회합니다. <- 페이지네이션으로 변경 예정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<List<ReportFindResponseDto>>> getAllReports(){
		return success(reportService.getAllReports());
	}

	@Operation(summary = "신고 상태 수정", description = "신고의 상태(어드민 처리 여부)를 변경합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> updateReport(
		@RequestBody @Valid ReportUpdateRequestDto reportUpdateRequestDto){
		return success(reportService.updateReport(reportUpdateRequestDto));
	}

}
