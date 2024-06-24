package com.gt.genti.adapter.in.web.user;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.adapter.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQStatusResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserPGREQController] 유저의 사진 생성 요청", description = "사진 생성 요청을 생성, 조회, 수정합니다.")
@RestController
@RequestMapping("/api/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPGREQController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Operation(summary = "내 요청 전체조회", description = "내가 요청한 사진생성요청 전체 조회", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.findAllPGREQByRequester(userDetails.getUser()));
	}

	@Operation(summary = "현재 진행중인 사진생성요청의 상태 조회", description = "작업이 진행중인 사진 생성요청이 있다면 해당 상태를 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@GetMapping("/pending")
	public ResponseEntity<ApiResult<PGREQStatusResponseDto>> hasPendingRequests(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getPGREQStatusIfPendingExists(userDetails.getUser()));
	}

	@Operation(summary = "완료되었지만 아직 확인하지 않은 요청 조회", description = "작업이 완료되어 유저가 최종확인해야하는 사진생성요청 결과(완성된사진)을 조회한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound)
	})
	@GetMapping("/not-verified-yet")
	public ResponseEntity<ApiResult<PGREQBriefFindByUserResponseDto>> findNotVerifiedCompletedPGREQ(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getByRequesterAndStatusIs(userDetails.getUser(),
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
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(
			pictureGenerateRequestUseCase.findPGREQByRequestAndId(userDetails.getUser(), pictureGenerateRequestId));
	}

	@Operation(summary = "완성된 사진 확인", description = "완성된 사진을 최종 확인")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("{pictureGenerateRequestId}/verify")
	public ResponseEntity<ApiResult<Boolean>> verifyCompletedPGREQ(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable(name = "pictureGenerateRequestId") Long pictureGenerateRequestId) {
		return success(pictureGenerateRequestUseCase.verifyCompletedPGREQ(userDetails.getUser(),
			pictureGenerateRequestId));
	}

	@Operation(summary = "사진생성요청하기", description = "사진생성요청을 생성한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.createPGREQ(userDetails.getUser(),
			pgreqSaveRequestDto.toCommand());
		return success(true);
	}

	@Operation(summary = "사진생성요청 수정", description = "이전에 생성한 사진생성요청을 수정한다", deprecated = true)
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateRequestNotFound),
		@EnumResponse(ResponseCode.PictureGenerateRequestAlreadyInProgress)
	})
	@PutMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.modifyPGREQ(userDetails.getUser(), pictureGenerateRequestId,
			pgreqSaveRequestDto);
		return success(true);
	}
}
