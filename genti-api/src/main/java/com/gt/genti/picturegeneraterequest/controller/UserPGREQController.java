package com.gt.genti.picturegeneraterequest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.user.model.AuthUser;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserPGREQController] 유저의 사진 생성 요청", description = "사진 생성 요청을 생성, 조회, 수정합니다.")
@RestController
@RequestMapping("/api/v1/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPGREQController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Operation(summary = "내 요청 전체조회", description = "내가 요청한 사진생성요청 전체 조회", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthUser Long userId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.findAllPGREQByRequester(userId));
	}

	@Operation(summary = "현재 진행중인 사진생성요청의 상태 조회", description = "작업이 진행중인 사진 생성요청이 있다면 해당 상태를 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@GetMapping("/pending")
	public ResponseEntity<ApiResult<PGREQStatusResponseDto>> hasPendingRequests(
		@AuthUser Long userId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.getPGREQStatusIfPendingExists(userId));
	}

	@Operation(summary = "완료되었지만 아직 확인하지 않은 요청 조회", description = "작업이 완료되어 유저가 최종확인해야하는 사진생성요청 결과(완성된사진)을 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@GetMapping("/not-verified-yet")
	public ResponseEntity<ApiResult<PGREQBriefFindByUserResponseDto>> findNotVerifiedCompletedPGREQ(
		@AuthUser Long userId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.getByRequesterAndStatusIs(userId,
				PictureGenerateRequestStatus.AWAIT_USER_VERIFICATION));
	}

	@Operation(summary = "내 요청 조회", description = "내가 요청한 사진생성요청을 자세히 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestVisibilityRestrictedToRequester)
	})
	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PGREQDetailFindByUserResponseDto>> getPictureGenerateRequestDetail(
		@PathVariable
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@AuthUser Long userId) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.findPGREQByRequestAndId(userId, pictureGenerateRequestId));
	}

	@Operation(summary = "완성된 사진 확인", description = "완성된 사진을 최종 확인")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/{pictureGenerateRequestId}/verify")
	public ResponseEntity<ApiResult<Boolean>> verifyCompletedPGREQ(
		@AuthUser Long userId,
		@PathVariable(name = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return GentiResponse.success(pictureGenerateRequestUseCase.verifyCompletedPGREQ(userId,
			pictureGenerateRequestId));
	}

	@Operation(summary = "사진생성요청하기", description = "사진생성요청을 생성한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthUser Long userId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.createPGREQ(userId,
			pgreqSaveRequestDto.toCommand());
		return GentiResponse.success(true);
	}

	@Operation(summary = "사진생성요청 수정", description = "이전에 생성한 사진생성요청을 수정한다", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestAlreadyInProgress)
	})
	@PutMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthUser Long userId,
		@PathVariable
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.modifyPGREQ(userId, pictureGenerateRequestId,
			pgreqSaveRequestDto);
		return GentiResponse.success(true);
	}
}
