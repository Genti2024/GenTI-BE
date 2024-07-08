package com.gt.genti.report.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.report.service.ReportService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[PGREQReportController] 유저의 신고 요청", description = "유저가 신고를 요청한다.")
@RestController
@RequestMapping("/api/users/reports")
@RequiredArgsConstructor
public class PGREQReportController {
	private final ReportService reportService;

	@Operation(summary = "신고", description = "사진생성응답을 신고합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound),
		@EnumResponse(ResponseCode.PictureGenerateResponseNotFound)}
	)
	@PostMapping("/v1")
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthUser Long userId,
		@RequestBody @Valid ReportCreateRequestDto reportCreateRequestDto
	) {
		return success(reportService.createReport(userId, reportCreateRequestDto));
	}

}
