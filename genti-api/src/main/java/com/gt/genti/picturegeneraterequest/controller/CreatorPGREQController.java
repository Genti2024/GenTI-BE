package com.gt.genti.picturegeneraterequest.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegeneraterequest.api.CreatorPGREQApi;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/creators/picture-generate-requests")
@RequiredArgsConstructor
public class CreatorPGREQController implements CreatorPGREQApi {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Logging(item = LogItem.PGREQ, action = LogAction.SEARCH, requester = LogRequester.CREATOR)
	@GetMapping("/assigned")
	public ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getAssignedPictureGenerateRequestBrief(
		@AuthUser Long userId
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestBrief(
			userId, PictureGenerateRequestStatus.ASSIGNING));
	}

	@Logging(item = LogItem.PGREQ_ACCEPT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateRequestId}/accept")
	public ResponseEntity<ApiResult<Boolean>> acceptPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "수락할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.acceptPictureGenerateRequest(userId, pictureGenerateRequestId));
	}

	@Logging(item = LogItem.PGREQ_REJECT, action = LogAction.UPDATE, requester = LogRequester.CREATOR)
	@PostMapping("/{pictureGenerateRequestId}/reject")
	public ResponseEntity<ApiResult<Boolean>> rejectPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "거절할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.rejectPictureGenerateRequest(userId, pictureGenerateRequestId));
	}

	@Logging(item = LogItem.PGREQ_INPROGESS, action = LogAction.SEARCH, requester = LogRequester.CREATOR)
	@GetMapping("/in-progress")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getInProgressPictureGenerateRequestDetail(
		@AuthUser Long userId
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail3(userId));
	}

	@Deprecated
	@Logging(item = LogItem.PGREQ, action = LogAction.VIEW, requester = LogRequester.CREATOR)
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getAssignedPictureGenerateRequestsAll(
		@AuthUser Long userId) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetailAll(
			userId));
	}

	@Deprecated
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