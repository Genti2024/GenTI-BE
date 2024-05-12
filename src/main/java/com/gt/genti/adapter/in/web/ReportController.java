package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.dto.ReportCreateRequestDto;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/reports")
@RequiredArgsConstructor
public class ReportController {
	private final ReportService reportService;

	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ReportCreateRequestDto reportCreateRequestDto
	) {
		return success(reportService.createReport(reportCreateRequestDto));
	}
}
