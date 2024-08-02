package com.gt.genti.report.controller;

import static com.gt.genti.model.LogAction.*;
import static com.gt.genti.model.LogItem.*;
import static com.gt.genti.model.LogRequester.USER;
import static com.gt.genti.response.GentiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.Logging;
import com.gt.genti.report.api.PGREQReportApi;
import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.report.service.ReportService;
import com.gt.genti.user.model.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/reports")
@RequiredArgsConstructor
public class PGREQReportController implements PGREQReportApi {
	private final ReportService reportService;

	@Logging(item = REPORT, action = CREATE, requester = USER)
	@PostMapping
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthUser Long userId,
		@RequestBody @Valid ReportCreateRequestDto reportCreateRequestDto
	) {
		return success(reportService.createReport(userId, reportCreateRequestDto));
	}

}
