package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.admin.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.valid.ValidEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminPGREQController] 어드민 사진생성요청 컨트롤러", description = "사진생성요청을 조회")
@RestController
@RequestMapping("/api/admin/picture-generate-requests")
@RequiredArgsConstructor
public class AdminPGREQController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Operation(summary = "사진생성요청 전체조회", description = "사진생성요청 전체를 매칭대상(어드민,공급자), 응답의 상태를 조건으로 조회하고  페이지네이션 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResult<Page<PGREQDetailFindByAdminResponseDto>>> getAllPictureGenerateRequest(
		@Parameter(description = "페이지 번호 (0-based)", example = "0", required = true)
		@RequestParam @NotNull @Min(0) int page,
		@Parameter(description = "페이지 당 요소 개수 >=1", example = "10", required = true)
		@RequestParam @NotNull @Min(1) int size,
		@Parameter(description = "정렬 조건 - 기본값 생성일시", example = "createdAt", schema = @Schema(allowableValues = {"id",
			"createdAt"}))
		@RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
		@Parameter(description = "정렬 방향 - 기본값 내림차순", example = "desc", schema = @Schema(allowableValues = {"acs",
			"desc"}))
		@RequestParam(name = "direction", defaultValue = "desc") String direction,
		@Parameter(description = "사진생성응답의 상태 ALL : 모든 상태 조회 SUBMITTED_FIRST : 공급자가 제출하여 어드민이 얼굴작업해야하는상태", example = "ADMIN_IN_PROGRESS", schema = @Schema(
			allowableValues = {"SUBMITTED_FIRST", "ADMIN_IN_PROGRESS", "SUBMITTED_FINAL", "COMPLETED", "ALL"}))
		@RequestParam(name = "status", defaultValue = "ALL") @ValidEnum(value = PictureGenerateResponseStatus.class, hasAllOption = true) String status,
		@Parameter(description = "true : 공급자를 거치지 않고 어드민에게 매칭된 요청 조회, false : 공급자에게 매칭된 요청 조회")
		@RequestParam(name = "matchToAdmin", defaultValue = "ALL") Boolean matchToAdmin
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		if ("ALL".equalsIgnoreCase(status)) {
			return success(pictureGenerateRequestUseCase.getAllByMatchToAdminIs(matchToAdmin, pageable));
		} else {
			return success(pictureGenerateRequestUseCase.getAllByPGRESStatusInAndMatchToAdminIs(
				List.of(PictureGenerateResponseStatus.valueOf(status)), matchToAdmin, pageable));
		}
	}
}
