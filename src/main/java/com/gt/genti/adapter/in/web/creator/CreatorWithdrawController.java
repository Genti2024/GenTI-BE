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
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.service.WithdrawService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorWithdrawController] 공급자 출금요청 컨트롤러", description = "공급자가 출금요청을 수행합니다.")
@RestController
@RequestMapping("/api/creators/withdraw")
@RequiredArgsConstructor
public class CreatorWithdrawController {
	private final WithdrawService withDrawService;

	@Operation(summary = "출금요청", description = "공급자가 작업한 정산결과를 바탕으로 출금요청을 생성합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<WithdrawCreateResponseDto>> createWithdrawRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return success(withDrawService.create(userDetails.getUser()));
	}

	@Operation(summary = "출금요청내역조회", description = "공급자의 모든 출금요청을 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("")
	public ResponseEntity<ApiResult<List<WithdrawCreateResponseDto>>> getWithdrawRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return success(withDrawService.findWithdrawList(userDetails.getUser()));
	}


}
