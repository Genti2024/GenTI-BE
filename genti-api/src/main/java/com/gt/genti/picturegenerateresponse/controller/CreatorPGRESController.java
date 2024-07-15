package com.gt.genti.picturegenerateresponse.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picturegenerateresponse.dto.request.MemoUpdateRequestDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByCreatorResponseDto;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.swagger.RequireImageUpload;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorPGRESController] 공급자의 사진생성응답 컨트롤러", description = "사진생성응답 작업 api")
@RestController
@RequestMapping("/api/v1/creators/picture-generate-responses")
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
	@Logging(item = LogItem.PGRES_PICTURE_CREATED_BY_CREATOR, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<Boolean>> updatePictureUrl(
		@AuthUser Long userId,
		@Parameter(description = "사진 url을 업데이트할 사진생성응답 Id", example = "1")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@RequestBody @Valid List<@Valid CommonPictureKeyUpdateRequestDto> commonPictureKeyUpdateRequestDtoList
	) {
		return success(
			pictureGenerateWorkService.updatePictureCreatedByCreatorList(pictureGenerateResponseId,
				commonPictureKeyUpdateRequestDtoList, userId));
	}

	@Operation(summary = "사진생성응답을 어드민에게 제출", description = "(사진 업로드 이후) 사진생성응답을 어드민에게 제출합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateResponseNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.SubmitBlockedDueToPictureGenerateResponseIsExpired),
		@EnumResponse(ResponseCode.DepositNotFound)
	})
	@Logging(item = LogItem.PGRES_SUBMIT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PGRESSubmitByCreatorResponseDto>> submitPictureGenerateResponse(
		@AuthUser Long userId,
		@Parameter(description = "제출할 사진생성응답 Id", example = "1")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId) {
		return success(
			pictureGenerateWorkService.submitToAdmin(userId, pictureGenerateResponseId));
	}

	@Deprecated
	@Operation(summary = "메모 추가", description = "사진생성응답에 메모를 추가합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PGRES_MEMO, action = LogAction.CREATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateResponseId}/memo")
	public ResponseEntity<ApiResult<Boolean>> updateMemo(
		@Parameter(description = "메모를 수정할 사진생성응답 Id", example = "1")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@RequestBody @Valid MemoUpdateRequestDto memoUpdateRequestDto) {
		return success(pictureGenerateWorkService.updateMemo(pictureGenerateResponseId, memoUpdateRequestDto));
	}
}