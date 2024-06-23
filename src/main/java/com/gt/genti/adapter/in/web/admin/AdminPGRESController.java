package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateWorkService;
import com.gt.genti.dto.admin.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.dto.admin.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.dto.admin.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.common.response.CommonPictureResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.swagger.RequireImageUpload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[AdminPGRESController] 어드민 사진생성응답 컨트롤러", description = "사진생성응답을 조회, 수정")
@RestController
@RequestMapping("/api/admin/picture-generate-responses")
@RequiredArgsConstructor
public class AdminPGRESController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Operation(summary = "사진생성응답 최종 제출", description = "사진생성응답을 최종 제출합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PGRESSubmitByAdminResponseDto>> submit(
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.submitFinal(pictureGenerateResponseId));
	}

	@Operation(summary = "응답에 최종 사진 리스트 추가", description = "사진생성응답에 최종 사진 리스트 추가")
	@RequireImageUpload
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> updatePictureList(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid List<@Valid CommonPictureKeyUpdateRequestDto> reuqestDtoList,
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.updatePictureListCreatedByAdmin(userDetails.getUser(), reuqestDtoList,
			pictureGenerateResponseId));
	}

	@Operation(summary = "담당자(어드민) 설정", description = "사진생성응답 담당 어드민 설정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateResponseId}/admin-in-charge")
	public ResponseEntity<ApiResult<PGRESUpdateAdminInChargeResponseDto>> updateAdminInCharge(
		@PathVariable Long pictureGenerateResponseId,
		@RequestBody PGRESUpdateAdminInChargeRequestDto requestDto) {
		return success(pictureGenerateWorkService.updateAdminInCharge(pictureGenerateResponseId, requestDto));
	}
}
