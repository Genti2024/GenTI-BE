package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.PromptOnlyExampleSaveRequestDto;
import com.gt.genti.dto.ExampleSaveRequestDto;
import com.gt.genti.dto.PromptOnlyExampleFindResponseDto;
import com.gt.genti.dto.ExampleWithPictureFindResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.ResponseExampleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/examples")
@RequiredArgsConstructor
public class ExampleController {
	private final ResponseExampleService responseExampleService;

	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

	@GetMapping("/prompt-only")
	public ResponseEntity<ApiResult<List<PromptOnlyExampleFindResponseDto>>> getAllPromptOnlyExamples() {
		return success(responseExampleService.getAllPromptOnlyExamples());
	}

	@PostMapping("/with-picture")
	public ResponseEntity<ApiResult<Boolean>> addResponseExample(
		@RequestBody List<ExampleSaveRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addResponseExamples(requestDtoList, userDetails.getId());
		return success(true);
	}

	@PostMapping("/prompt-only")
	public ResponseEntity<ApiResult<Boolean>> addPromptOnlyExample(
		@RequestBody List<PromptOnlyExampleSaveRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addPromptOnlyExamples(requestDtoList, userDetails.getId());
		return success(true);
	}

}
