package com.gt.genti.matchingstrategy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.matchingstrategy.controller.dto.request.MatchingStrategyUpdateRequestDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.service.RequestMatchService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminMatchStrategyController] 어드민 요청-공급자 매칭 전략 컨트롤러", description = "요청-공급자 매칭 전략을 조회,수정")
@RestController
@RequestMapping("/api/v1/admin/match-strategy")
@RequiredArgsConstructor
public class AdminMatchStrategyController {
	@Operation(summary = "매칭전략 조회", description = "현재 시스템의 매칭전략을 조회합니다.", hidden = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<String>> getMatchStrategy() {
		return GentiResponse.success(RequestMatchService.CURRENT_STRATEGY.getStringValue());
	}

	@Operation(summary = "매칭 전략 수정", description = "요청 - 공급자,어드민 매칭 전략 수정", hidden = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<String>> setMatchStrategy(
		@RequestBody MatchingStrategyUpdateRequestDto requestDto) {
		return GentiResponse.success(
			RequestMatchService.changeMatchingStrategy(requestDto.getRequestMatchStrategy()).getResponse());
	}

}
