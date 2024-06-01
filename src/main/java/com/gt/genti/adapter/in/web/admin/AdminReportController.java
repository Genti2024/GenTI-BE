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
import com.gt.genti.dto.ReportFindResponseDto;
import com.gt.genti.dto.ReportUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {
	private final ReportService reportService;
	@GetMapping("")
	public ResponseEntity<ApiResult<List<ReportFindResponseDto>>> getAllReports(){
		return success(reportService.getAllReports());
	}

	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> updateReport(@RequestBody ReportUpdateRequestDto reportUpdateRequestDto){
		return success(reportService.updateReport(reportUpdateRequestDto));
	}

}
