package com.gt.genti.picturegeneraterequest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@AuthorizedUser
@Tag(name = "[UserPGREQController] 유저의 사진 생성 요청", description = "사진 생성 요청을 생성, 조회, 수정합니다.")
public interface UserPGREQApi {

	@Deprecated
	@Operation(summary = "내 요청 전체조회", description = "내가 요청한 사진생성요청 전체 조회", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthUser Long userId
	);

	@Operation(summary = "현재 진행중인 사진생성요청의 상태 조회", description = "작업이 진행중인 사진 생성요청이 있다면 해당 상태를 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	ResponseEntity<ApiResult<PGREQStatusResponseDto>> getPendingPGRESStatus(
		@AuthUser Long userId
	);

	@Operation(summary = "취소된 사진생성요청 확인(폐기)처리", description = "사진생성요청이 오류로 인해 취소된 경우 실행하여 초기화한 후 새로운 요청을 할 수 있도록 변경")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.UserNotFound)
	})
	ResponseEntity<ApiResult<Boolean>> confirmCanceledPGREQ(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateRequestId")
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId
	);

	@Operation(summary = "사진생성요청하기", description = "사진생성요청을 생성한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthUser Long userId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto);

	@Deprecated
	@Operation(summary = "사진생성요청 수정", description = "이전에 생성한 사진생성요청을 수정한다", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestAlreadyInProgress)
	})
	ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateRequestId")
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto);
}
