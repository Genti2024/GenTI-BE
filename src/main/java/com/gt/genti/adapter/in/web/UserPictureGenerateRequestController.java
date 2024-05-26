package com.gt.genti.adapter.in.web;

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
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDtoForUser;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPictureGenerateRequestController {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@CheckUserIsQuit
	@GetMapping("")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDtoForUser>>> getMyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getAllPictureGenerateRequestForUser(userDetails.getId()));
	}

	@CheckUserIsQuit
	@GetMapping("/active")
	public ResponseEntity<ApiResult<PictureGenerateRequestDetailResponseDtoForUser>> getMyRecentPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(
			pictureGenerateRequestUseCase.getPictureGenerateRequestForUser(userDetails.getId()));
	}

	@GetMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PictureGenerateRequestDetailResponseDto>> getPictureGenerateRequestDetail(
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateRequestUseCase.getPictureGenerateRequestById(pictureGenerateRequestId));
	}

	@CheckUserIsQuit
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {
		pictureGenerateRequestUseCase.createPictureGenerateRequest(userDetails.getId(),
			pictureGenerateRequestRequestDto);
		return success(true);
	}

	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto) {
		pictureGenerateRequestUseCase.modifyPictureGenerateRequest(userDetails.getId(),
			pictureGenerateRequestModifyDto);
		return success(true);
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestBriefResponseDto>>> getAllMyRequests(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateRequestUseCase.getAllMyPictureGenerateRequests(userDetails.getId()));
	}

}
