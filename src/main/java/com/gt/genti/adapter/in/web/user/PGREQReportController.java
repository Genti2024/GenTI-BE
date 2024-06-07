package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.dto.admin.ReportSaveRequestDto;
import com.gt.genti.other.auth.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/reports")
@RequiredArgsConstructor
public class PGREQReportController {
	private final ReportService reportService;

	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid ReportSaveRequestDto reportSaveRequestDto
	) {
		return success(reportService.createReport(reportSaveRequestDto));
	}
}
