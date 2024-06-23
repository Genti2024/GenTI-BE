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
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

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
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid ReportCreateRequestDto reportCreateRequestDto
	) {
		return success(reportService.createReport(userDetails.getUser(), reportCreateRequestDto));
	}

}
