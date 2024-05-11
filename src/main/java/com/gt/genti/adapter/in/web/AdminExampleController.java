package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.AddPromptOnlyExampleRequestDto;
import com.gt.genti.dto.AddResponseExampleRequestDto;
import com.gt.genti.dto.PromptOnlyExampleResponseDto;
import com.gt.genti.dto.ResponseExampleResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.ResponseExampleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/examples")
@RequiredArgsConstructor
public class AdminExampleController {
	private final ResponseExampleService responseExampleService;

	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ResponseExampleResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

	@GetMapping("/prompt-only")
	public ResponseEntity<ApiResult<List<PromptOnlyExampleResponseDto>>> getAllPromptOnlyExamples() {
		return success(responseExampleService.getAllPromptOnlyExamples());
	}

	@PostMapping("/with-picture")
	public ResponseEntity<ApiResult<Boolean>> addResponseExample(
		@RequestBody List<AddResponseExampleRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addResponseExamples(requestDtoList, userDetails.getId());
		return success(true);
	}

	@PostMapping("/prompt-only")
	public ResponseEntity<ApiResult<Boolean>> addPromptOnlyExample(
		@RequestBody List<AddPromptOnlyExampleRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addPromptOnlyExamples(requestDtoList, userDetails.getId());
		return success(true);
	}

}
