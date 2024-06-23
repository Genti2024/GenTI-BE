package com.gt.genti.adapter.in.web.creator;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateWorkService;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.admin.response.PGREQDetailFindResponseDto;
import com.gt.genti.dto.creator.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[CreatorPGREQController] 공급자의 사진생성요청 컨트롤러", description = "사진생성요청 관련 작업 api")
@RestController
@RequestMapping("/api/creators/picture-generate-requests")
@RequiredArgsConstructor
public class CreatorPGREQController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Operation(summary = "내게 매칭된 요청 조회", description = "나(공급자)에게 매칭된 사진생성요청을 조회합니다. (수락할지 말지 선택하기위한 최소 정보 보여주기)")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/assigned")
	public ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getAssignedPictureGenerateRequestBrief(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestBrief(
			userDetails.getUser(), PictureGenerateRequestStatus.ASSIGNING));
	}

	@Operation(summary = "매칭 수락", description = "내게(공급자) 매칭된 사진생성요청을 수락합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateRequestId}/accept")
	public ResponseEntity<ApiResult<Boolean>> acceptPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.acceptPictureGenerateRequest(userDetails.getUser(), pictureGenerateRequestId));
	}

	@Operation(summary = "매칭 거절", description = "내게(공급자) 매칭된 사진생성요청을 거절합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateRequestId}/reject")
	public ResponseEntity<ApiResult<Boolean>> rejectPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(
			pictureGenerateWorkService.rejectPictureGenerateRequest(userDetails.getUser(), pictureGenerateRequestId));
	}

	@Operation(summary = "작업 진행중인(최대 3개) 사진생성요청 조회", description = "내가(공급자)진행중인 사진생성요청&응답을 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/in-progress")
	public ResponseEntity<ApiResult<List<PGREQDetailFindResponseDto>>> getInProgressPictureGenerateRequestDetail(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail3(userDetails.getUser()));
	}

	@Deprecated
	@Operation(summary = "작업한(하는중인) 사진생성요청&응답 전체조회", description = "내가(공급자) 작업한(하고있는) 사진생성요청&응답을 전체 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQDetailFindResponseDto>>> getAssignedPictureGenerateRequestsAll(
		@AuthenticationPrincipal
		UserDetailsImpl userDetails) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetailAll(
			userDetails.getUser()));
	}

	@Deprecated
	@Operation(summary = "사진생성요청&응답 1개 자세히조회", description = "내가(공급자) 작업중인 사진생성요청&응답 1개를 자세히 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PGREQDetailFindResponseDto>> getPictureGenerateRequestDetail(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail(
			userDetails.getUser(), pictureGenerateRequestId));
	}

}