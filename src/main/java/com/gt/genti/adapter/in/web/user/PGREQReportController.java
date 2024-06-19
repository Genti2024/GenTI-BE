package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.ReportService;
import com.gt.genti.dto.user.request.ReportCreateRequestDto;
import com.gt.genti.other.auth.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저의 신고 요청", description = "유저가 신고를 요청한다.")
@RestController
@RequestMapping("/api/users/reports")
@RequiredArgsConstructor
public class PGREQReportController {
	private final ReportService reportService;

	@Operation(summary = "생성된 사진 신고 기능", description = "생성된 사진 신고 기능")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "404", description = "해당 사진을 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "해당 사진생성요청을 찾을 수 없음, DomainErrorCode.UserNotFound"),
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid ReportCreateRequestDto reportCreateRequestDto
	) {
		return success(reportService.createReport(reportCreateRequestDto));
	}
}
