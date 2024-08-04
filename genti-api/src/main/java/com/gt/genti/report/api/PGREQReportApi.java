package com.gt.genti.report.api;

import static com.gt.genti.error.ResponseCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.gt.genti.report.dto.request.ReportCreateRequestDto;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@AuthorizedUser
@Tag(name = "[PGREQReportController] 유저의 신고 요청", description = "유저가 신고를 요청한다.")
public interface PGREQReportApi {

	@Operation(summary = "신고", description = "사진생성응답을 신고합니다.")
	@EnumResponses(value = {
		@EnumResponse(OK),
		@EnumResponse(UserNotFound),
		@EnumResponse(PictureGenerateResponseNotFound)}
	)
	ResponseEntity<ApiResult<Boolean>> createReport(
		@AuthUser Long userId,
		@RequestBody @Valid ReportCreateRequestDto reportCreateRequestDto
	);
}
