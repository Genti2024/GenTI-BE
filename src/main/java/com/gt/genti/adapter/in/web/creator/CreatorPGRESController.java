package com.gt.genti.adapter.in.web.creator;

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
import com.gt.genti.dto.common.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.dto.creator.request.MemoUpdateRequestDto;
import com.gt.genti.dto.creator.response.PGRESSubmitByCreatorResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;
import com.gt.genti.other.swagger.RequireImageUpload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorPGRESController] 공급자의 사진생성응답 컨트롤러", description = "사진생성응답 작업 api")
@RestController
@RequestMapping("/api/creators/picture-generate-responses")
@RequiredArgsConstructor
public class CreatorPGRESController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@RequireImageUpload
	@Operation(summary = "사진생성응답에 사진 Key 리스트 추가", description = "사진생성응답에 공급자 제출사진업로드 결과 Key 리스트를 추가합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateResponseNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator)

	})
	@PostMapping("/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<Boolean>> updatePictureUrl(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@Parameter(description = "사진 url을 업데이트할 사진생성응답 Id", example = "1")
		@PathVariable Long pictureGenerateResponseId,
		@RequestBody @Valid List<@Valid CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList
	) {
		return success(
			pictureGenerateWorkService.updatePictureCreatedByCreatorList(pictureGenerateResponseId,
				commonPictureKeyUpdateRequestDtoList, userDetails.getUser()));
	}

	@Operation(summary = "사진생성응답을 어드민에게 제출", description = "(사진 업로드 이후) 사진생성응답을 어드민에게 제출합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateResponseNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.SubmitBlockedDueToPictureGenerateResponseIsExpired),
		@EnumResponse(ResponseCode.DepositNotFound)
	})
	@PostMapping("/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PGRESSubmitByCreatorResponseDto>> submitPictureGenerateResponse(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@Parameter(description = "제출할 사진생성응답 Id", example = "1")
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.submitToAdmin(userDetails.getUser(), pictureGenerateResponseId));
	}

	@Deprecated
	@Operation(summary = "메모 추가", description = "사진생성응답에 메모를 추가합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateResponseId}/memo")
	public ResponseEntity<ApiResult<Boolean>> updateMemo(
		@Parameter(description = "메모를 수정할 사진생성응답 Id", example = "1")
		@PathVariable Long pictureGenerateResponseId,
		@RequestBody @Valid MemoUpdateRequestDto memoUpdateRequestDto) {
		return success(pictureGenerateWorkService.updateMemo(pictureGenerateResponseId, memoUpdateRequestDto));
	}
}