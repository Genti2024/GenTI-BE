package com.gt.genti.picturegeneraterequest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByCreatorResponseDto;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedCreator;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@AuthorizedCreator
@Tag(name = "[CreatorPGREQController] 공급자의 사진생성요청 컨트롤러", description = "사진생성요청 관련 작업 api")
public interface CreatorPGREQApi {

	@Operation(summary = "내게 매칭된 요청 조회", description = "나(공급자)에게 매칭된 사진생성요청을 조회합니다. (수락할지 말지 선택하기위한 최소 정보 보여주기)")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getAssignedPictureGenerateRequestBrief(
		@AuthUser Long userId
	);

	@Operation(summary = "매칭 수락", description = "내게(공급자) 매칭된 사진생성요청을 수락합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.CreatorNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	ResponseEntity<ApiResult<Boolean>> acceptPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "수락할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId);

	@Operation(summary = "매칭 거절", description = "내게(공급자) 매칭된 사진생성요청을 거절합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotAssignedToCreator),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	ResponseEntity<ApiResult<Boolean>> rejectPictureGenerateRequest(
		@AuthUser Long userId,
		@Parameter(description = "거절할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId);

	@Operation(summary = "작업 진행중인(최대 3개) 사진생성요청 조회", description = "내가(공급자)진행중인 사진생성요청&응답을 조회합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound),
	})
	ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getInProgressPictureGenerateRequestDetail(
		@AuthUser Long userId
	);

	@Deprecated
	@Operation(summary = "작업한(하는중인) 사진생성요청&응답 전체조회", description = "내가(공급자) 작업한(하고있는) 사진생성요청&응답을 전체 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<List<PGREQBriefFindByCreatorResponseDto>>> getAssignedPictureGenerateRequestsAll(
		@AuthUser Long userId);

	@Deprecated
	@Operation(summary = "사진생성요청&응답 1개 자세히조회", description = "내가(공급자) 작업중인 사진생성요청&응답 1개를 자세히 조회합니다.", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<PGREQBriefFindByCreatorResponseDto>> getPictureGenerateRequestDetail(
		@AuthUser Long userId,
		@Parameter(description = "조회할 사진생성요청의 id", example = "1")
		@PathVariable(value = "pictureGenerateRequestId") Long pictureGenerateRequestId);
}
