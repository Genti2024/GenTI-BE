package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.admin.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.service.ResponseExampleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserExampleController] 유저 사진생성 예시 컨트롤러", description = "사진생성 예시를 조회합니다.")
@RestController
@RequestMapping("/api/user/examples")
@RequiredArgsConstructor
public class UserExampleController {
	private final ResponseExampleService responseExampleService;

	@Operation(summary = "예시 전체조회", description = "예시 사진&프롬프트를 전체 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

}
