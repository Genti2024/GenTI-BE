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
import com.gt.genti.dto.user.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.PGREQSaveRequestDto;
import com.gt.genti.dto.user.PGREQUpdateRequestDto;
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

	@GetMapping("")
	@Operation(summary = "유저의 사진생성요청 전체 조회", description = "내가 생성했던 요청들 전체 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class)))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "요청을 찾을 수 없음, DomainErrorCode.PictureGenerateRequestNotFound"),
	})
	public ResponseEntity<ApiResult<List<PGREQDetailFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getAllPictureGenerateRequestForUser(userDetails.getId()));
	}

	@Deprecated
	@Operation(summary = "유저의 활성된 사진생성요청 1개 조회", description = "작업이 진행중인 사진생성요청을 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "현재 진행중인 요청이 없습니다, DomainErrorCode.ActivePictureGenerateRequestNotExists"),
	})
	@GetMapping("/active/deprecated")
	public ResponseEntity<ApiResult<PGREQDetailFindByUserResponseDto>> getMyActivePictureGenerateRequest_Deprecated(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.findActivePGREQByUser(userDetails.getId()));
	}

	@Operation(summary = "유저의 활성된 사진생성요청 1개 조회", description = "작업이 진행중인 사진생성요청을 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "현재 진행중인 요청이 없습니다, DomainErrorCode.ActivePictureGenerateRequestNotExists"),
	})
	@GetMapping("/active")
	public ResponseEntity<ApiResult<Boolean>> getMyActivePictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.isActivePGREQExists(userDetails.getId()));
	}

	@Operation(summary = "유저의 사진생성요청 조회", description = "사진생성요청 id로 사진생성요청을 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "요청을 찾을 수 없음, DomainErrorCode.PictureGenerateRequestNotFound"),
		@ApiResponse(responseCode = "403", description = "사진생성요청을 한 유저 본인만 조회할 수 있습니다., DomainErrorCode.OnlyRequesterCanViewRequest"),
	})
	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PGREQDetailFindByUserResponseDto>> getPictureGenerateRequestDetail(
		@PathVariable
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(
			pictureGenerateRequestUseCase.findPGREQByUserAndId(userDetails.getId(), pictureGenerateRequestId));
	}

	@Operation(summary = "사진생성요청 생성", description = "사진생성요청을 생성한다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음, DomainErrorCode.UserNotFound"),
		@ApiResponse(responseCode = "404", description = "요청을 찾을 수 없음, DomainErrorCode.PictureGenerateRequestNotFound"),
		@ApiResponse(responseCode = "403", description = "사진생성요청을 한 유저 본인만 조회할 수 있습니다., DomainErrorCode.OnlyRequesterCanViewRequest"),
	})
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid PGREQSaveRequestDto PGREQSaveRequestDto) {
		pictureGenerateRequestUseCase.createPictureGenerateRequest(userDetails.getId(),
			PGREQSaveRequestDto);
		return success(true);
	}

	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid PGREQUpdateRequestDto PGREQUpdateRequestDto) {
		pictureGenerateRequestUseCase.modifyPictureGenerateRequest(userDetails.getId(),
			PGREQUpdateRequestDto);
		return success(true);
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllMyRequests(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateRequestUseCase.getAllMyPictureGenerateRequests(userDetails.getId()));
	}

}
