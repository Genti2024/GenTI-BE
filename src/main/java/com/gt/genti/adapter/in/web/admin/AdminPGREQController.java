package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateRequestService;
import com.gt.genti.dto.admin.response.PGREQDetailFindResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminPGREQController] 어드민 사진생성요청 컨트롤러", description = "사진생성요청을 조회")
@RestController
@RequestMapping("/api/admin/picture-generate-requests")
@RequiredArgsConstructor
public class AdminPGREQController {
	private final PictureGenerateRequestService pictureGenerateRequestService;

	@Operation(summary = "사진생성요청 전체조회", description = "사진생성요청 전체를 페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResult<Page<PGREQDetailFindResponseDto>>> getAllPictureGenerateRequest(
		@RequestParam @NotNull @Min(0) int page,
		@RequestParam @NotNull @Min(1) int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "desc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(pictureGenerateRequestService.getAllPictureGenerateRequest(pageable));
	}

}
