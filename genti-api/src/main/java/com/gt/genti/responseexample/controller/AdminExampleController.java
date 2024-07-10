package com.gt.genti.responseexample.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.responseexample.dto.request.ExampleSaveRequestDto;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.responseexample.service.ResponseExampleService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.swagger.RequireImageUpload;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminExampleController] 어드민 사진생성결과 예시 컨트롤러", description = "사진생성결과 예시를 조회, 업로드 합니다.")
@RestController
@RequestMapping("/api/v1/admin/examples")
@RequiredArgsConstructor
public class AdminExampleController {
	private final ResponseExampleService responseExampleService;

	@Operation(summary = "전체 예시 조회", description = "전체 예시 사진&프롬프트를 페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<Page<ExampleWithPictureFindResponseDto>>> getAllResponseExamples(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam(name = "page", defaultValue = "0") @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam(name = "size", defaultValue = "10") @NotNull @Min(0) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(responseExampleService.getAllResponseExamplesPagination(pageable));
	}

	@Operation(summary = "예시 생성", description = "예시 사진&프롬프트 추가합니다.")
	@RequireImageUpload
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/with-picture")
	public ResponseEntity<ApiResult<Boolean>> addResponseExample(
		@RequestBody @Valid List<@Valid ExampleSaveRequestDto> requestDtoList,
		@AuthUser Long userId
	) {
		responseExampleService.addResponseExamples(
			requestDtoList.stream().map(ExampleSaveRequestDto::toCommand).toList(), userId);
		return success(true);
	}

}