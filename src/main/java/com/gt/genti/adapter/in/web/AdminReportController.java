package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.dto.ReportResponseDto;
import com.gt.genti.other.util.ApiUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {
	ReportService reportService;
	@GetMapping("")
	public ResponseEntity<ApiResult<List<ReportResponseDto>>> getAllReports(){
		return success(reportService.getAllReports());
	}

}
