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
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.error.DomainErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저의 사진 생성 요청", description = "사진 생성 요청을 생성, 조회, 수정합니다.")
@RestController
@RequestMapping("/api/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPGREQController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@GetMapping("/all")
	@Operation(summary = "유저의 사진생성요청 전체 조회", description = "내가 생성했던 요청들을 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = PGREQBriefFindByUserResponseDto.class)))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}
		),
		@ApiResponse(responseCode = "204", description = "유저의 사진생성요청이 없음, DomainErrorCode.PictureGenerateRequestNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}),
	})
	public ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.findAllPGREQByRequester(userDetails.getUser()));
	}

	@Operation(summary = "유저 작업중인 사진생성요청 유무 조회", description = "작업이 진행중인 사진생성요청이 있는지 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}
		),
	})
	@GetMapping("/pending")
	public ResponseEntity<ApiResult<Boolean>> hasPendingRequests(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.isPendingPGREQExists(userDetails.getUser()));
	}

	@Operation(summary = "유저의 완료된 사진생성요청결과 조회", description = "작업이 완료된 사진생성요청 결과(완성된사진)을 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQBriefFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}
		),
	})
	@GetMapping("/completed")
	public ResponseEntity<ApiResult<PGREQBriefFindByUserResponseDto>> findCompletedPGREQ(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.findCompletedPGREQByRequester(userDetails.getUser()));
	}

	@Operation(summary = "유저의 사진생성요청 자세히 조회", description = "사진생성요청 id로 사진생성요청을 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}
		),
		@ApiResponse(responseCode = "404", description = "요청을 찾을 수 없음, DomainErrorCode.PictureGenerateRequestNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}),
		@ApiResponse(responseCode = "403", description = "사진생성요청을 한 유저 본인만 조회할 수 있습니다., DomainErrorCode.OnlyRequesterCanViewRequest",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))}),
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

	@Operation(summary = "사진생성요청 생성", description = "사진생성요청을 생성한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = SuccessExample.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))})}

	)
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {

		pictureGenerateRequestUseCase.createPGREQ(userDetails.getUser(),
			pgreqSaveRequestDto.toCommand());
		return success(true);
	}

	@Operation(summary = "사진생성요청 수정", description = "이전에 생성한 사진생성요청을 수정한다..")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = SuccessExample.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = FailedExample.class))})}

	)
	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid PGREQUpdateRequestDto PGREQUpdateRequestDto) {
		pictureGenerateRequestUseCase.modifyPGREQ(userDetails.getUser(),
			PGREQUpdateRequestDto);
		return success(true);
	}

	static class FailedExample extends ApiResult<Boolean> {
		public FailedExample() {
			super(false, null, ExpectedException.withoutLogging(DomainErrorCode.NotSupportedTemp));
		}
	}

	static class SuccessExample extends ApiResult<Boolean> {
		public SuccessExample() {
			super(true, null);
		}
	}
}
