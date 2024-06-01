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
import com.gt.genti.dto.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.PGREQUpdateRequestDto;
import com.gt.genti.dto.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.PGREQSaveRequestDto;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저의 사진 생성 요청", description = "사진 생성 요청을 생성, 조회, 수정합니다.")
@RestController
@RequestMapping("/api/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPGREQController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@GetMapping("")
	@Operation(summary = "전체 조회", description = "내가 생성했던 요청들 전체 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = {@Content(mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = PGREQDetailFindByUserResponseDto.class)))}),
		@ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ExpectedException.class))),
		@ApiResponse(responseCode = "404", description = "요청을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ExpectedException.class))),
	})
	public ResponseEntity<ApiResult<List<PGREQDetailFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getAllPictureGenerateRequestForUser(userDetails.getId()));
	}

	@GetMapping("/active")
	public ResponseEntity<ApiResult<PGREQDetailFindByUserResponseDto>> getMyRecentPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getPictureGenerateRequestForUser(userDetails.getId()));
	}

	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PGREQDetailFindResponseDto>> getPictureGenerateRequestDetail(
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateRequestUseCase.getPictureGenerateRequestById(pictureGenerateRequestId));
	}

	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PGREQSaveRequestDto PGREQSaveRequestDto) {
		pictureGenerateRequestUseCase.createPictureGenerateRequest(userDetails.getId(),
			PGREQSaveRequestDto);
		return success(true);
	}

	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PGREQUpdateRequestDto PGREQUpdateRequestDto) {
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
