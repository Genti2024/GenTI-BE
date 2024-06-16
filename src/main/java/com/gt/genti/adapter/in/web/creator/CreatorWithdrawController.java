package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.creator.response.WithdrawCreateResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.WithdrawService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators/withdraw")
@RequiredArgsConstructor
public class CreatorWithdrawController {
	private final WithdrawService withDrawService;

	@PostMapping("")
	public ResponseEntity<ApiResult<WithdrawCreateResponseDto>> createWithdrawRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return success(withDrawService.create(userDetails.getUser()));
	}

	@GetMapping("")
	public ResponseEntity<ApiResult<List<WithdrawCreateResponseDto>>> getWithdrawRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return success(withDrawService.findWithdrawList(userDetails.getUser()));
	}


}
