package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.RequestMatchService;
import com.gt.genti.dto.admin.request.MatchingStrategyUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/match-strategy")
@RequiredArgsConstructor
public class AdminMatchStrategyController {

	@GetMapping("")
	public ResponseEntity<ApiResult<String>> getMatchStrategy() {
		return success(RequestMatchService.CURRENT_STRATEGY.getStringValue());
	}

	@PostMapping("")
	public ResponseEntity<ApiResult<String>> setMatchStrategy(
		@RequestBody MatchingStrategyUpdateRequestDto requestDto) {
		return success(RequestMatchService.changeMatchingStrategy(requestDto.getRequestMatchStrategy()).getResponse());
	}

}
