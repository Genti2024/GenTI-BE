package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.PromptOnlyExampleFindResponseDto;
import com.gt.genti.dto.ExampleWithPictureFindResponseDto;
import com.gt.genti.service.ResponseExampleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/examples")
@RequiredArgsConstructor
public class UserExampleController {
	private final ResponseExampleService responseExampleService;

	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

	@GetMapping("/prompt-only")
	public ResponseEntity<ApiResult<List<PromptOnlyExampleFindResponseDto>>> getAllPromptOnlyExamples() {
		return success(responseExampleService.getAllPromptOnlyExamples());
	}

}
