package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.SettlementFindResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.SettlementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class SettlementController {
	private final SettlementService settlementService;
	@GetMapping("/settlements")
	public ResponseEntity<ApiResult<List<SettlementFindResponseDto>>> getMySettlements(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		//TODO 출금내역또한 나와야한다.
		// edited at 2024-05-19
		// author 서병렬

		return success(settlementService.getAllSettlements(userDetails.getUser()));
	}
}