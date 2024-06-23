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
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.swagger.RequireImageUpload;
import com.gt.genti.service.ResponseExampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminExampleController] 어드민 사진생성결과 예시 컨트롤러", description = "사진생성결과 예시를 조회, 업로드 합니다.")
@RestController
@RequestMapping("/api/admin/examples")
@RequiredArgsConstructor
public class AdminExampleController {
	private final ResponseExampleService responseExampleService;

	@Operation(summary = "전체 예시 조회", description = "전체 예시 사진&프롬프트를 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

	@Operation(summary = "예시 생성",description = "예시 사진&프롬프트 추가합니다.")
	@RequireImageUpload
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/with-picture")
	public ResponseEntity<ApiResult<Boolean>> addResponseExample(
		@RequestBody @Valid List<@Valid ExampleSaveRequestDto> requestDtoList,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		responseExampleService.addResponseExamples(
			requestDtoList.stream().map(ExampleSaveRequestDto::toCommand).toList(), userDetails.getUser());
		return success(true);
	}

}
