package com.gt.genti.picturegeneraterequest.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorPGREQController] 공급자의 사진생성요청 컨트롤러", description = "사진생성요청 관련 작업 api")
@RestController
@RequestMapping("/api/v1/creators/picture-generate-requests")
@RequiredArgsConstructor
public class CreatorPGREQController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Operation(summary = "내게 매칭된 요청 조회", description = "나(공급자)에게 매칭된 사진생성요청을 조회합니다. (수락할지 말지 선택하기위한 최소 정보 보여주기)")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@Logging(item = LogItem.PGREQ, action = LogAction.SEARCH, requester = LogRequester.CREATOR)
	@GetMapping("/assigned")
	public ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getAssignedPictureGenerateRequestBrief(
		@AuthUser Long userId
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestBrief(
			userId, PictureGenerateRequestStatus.ASSIGNING));
	}

	@Operation(summary = "매칭 수락", description = "내게(공급자) 매칭된 사진생성요청을 수락합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@Logging(item = LogItem.PGREQ_ACCEPT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateRequestId}/accept")
	public ResponseEntity<ApiResult<Boolean>> acceptPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "수락할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.acceptPictureGenerateRequest(userId, pictureGenerateRequestId));
	}


	@Operation(summary = "매칭 거절", description = "내게(공급자) 매칭된 사진생성요청을 거절합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@Logging(item = LogItem.PGREQ_REJECT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateRequestId}/reject")
	public ResponseEntity<ApiResult<Boolean>> rejectPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "거절할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.rejectPictureGenerateRequest(userId, pictureGenerateRequestId));
	}

	@Operation(summary = "작업 진행중인(최대 3개) 사진생성요청 조회", description = "내가(공급자)진행중인 사진생성요청&응답을 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
	})
	@Logging(item = LogItem.PGREQ_INPROGESS, action = LogAction.SEARCH, requester = LogRequester.CREATOR)
	@GetMapping("/in-progress")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getInProgressPictureGenerateRequestDetail(
		@AuthUser Long userId
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail3(userId));
	}

	@Deprecated
	@Operation(summary = "작업한(하는중인) 사진생성요청&응답 전체조회", description = "내가(공급자) 작업한(하고있는) 사진생성요청&응답을 전체 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PGREQ, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getAssignedPictureGenerateRequestsAll(
		@AuthUser Long userId) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetailAll(
			userId));
	}

	@Deprecated
	@Operation(summary = "사진생성요청&응답 1개 자세히조회", description = "내가(공급자) 작업중인 사진생성요청&응답 1개를 자세히 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PGREQ, action = LogAction.SEARCH, requester = LogRequester.CREATOR)
	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getPictureGenerateRequestDetail(
		@AuthUser Long userId,
		@Parameter(description = "조회할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail(
			userId, pictureGenerateRequestId));
	}

}