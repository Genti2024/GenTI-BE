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

import com.gt.genti.dto.admin.request.ExampleSaveRequestDto;
import com.gt.genti.dto.admin.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.service.ResponseExampleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/examples")
@RequiredArgsConstructor
public class AdminExampleController {
	private final ResponseExampleService responseExampleService;

	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

	@PostMapping("/with-picture")
	public ResponseEntity<ApiResult<Boolean>> addResponseExample(
		@RequestBody @Valid List<ExampleSaveRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addResponseExamples(requestDtoList.stream().map(ExampleSaveRequestDto::toCommand).toList(), userDetails.getUser());
		return success(true);
	}

}
