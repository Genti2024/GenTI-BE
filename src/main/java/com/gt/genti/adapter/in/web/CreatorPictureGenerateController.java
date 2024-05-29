package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateWorkService;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateResponseSubmitDto;
import com.gt.genti.dto.UpdateMemoRequestDto;
import com.gt.genti.dto.UpdatePictureUrlRequestDto;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorPictureGenerateController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@GetMapping("/picture-generate-requests/assigned")
	public ResponseEntity<ApiResult<PictureGenerateRequestBriefResponseDto>> getAssignedPictureGenerateRequestBrief(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestBrief(
			userDetails.getId(), PictureGenerateRequestStatus.ASSIGNING));
	}

	@GetMapping("/picture-generate-requests/in-progress")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDto>>> getInProgressPictureGenerateRequestDetail(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail3(userDetails.getId()));
	}

	@PostMapping("/picture-generate-requests/{pictureGenerateRequestId}/accept")
	public ResponseEntity<ApiResult<Boolean>> acceptPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateWorkService.acceptPictureGenerateRequest(userDetails.getId(), pictureGenerateRequestId));
	}

	@PostMapping("/picture-generate-requests/{pictureGenerateRequestId}/reject")
	public ResponseEntity<ApiResult<Boolean>> rejectPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateWorkService.rejectPictureGenerateRequest(userDetails.getId(), pictureGenerateRequestId));
	}

	@GetMapping("/picture-generate-requests/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<PictureGenerateRequestDetailResponseDto>> getPictureGenerateRequestDetail(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateRequestId) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetail(
			userDetails.getId(), pictureGenerateRequestId));
	}

	@GetMapping("/picture-generate-requests/all")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDto>>> getAssignedPictureGenerateRequestsAll(
		@AuthenticationPrincipal
		UserDetailsImpl userDetails) {
		return success(pictureGenerateWorkService.getPictureGenerateRequestDetailAll(
			userDetails.getId()));
	}

	@PostMapping("/picture-generate-responses/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<Boolean>> updatePictureUrl(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateResponseId,
		@RequestBody List<UpdatePictureUrlRequestDto> updatePictureUrlRequestDtoList
	) {
		return success(
			pictureGenerateWorkService.updatePictureCreatedByCreatorList(pictureGenerateResponseId,
				updatePictureUrlRequestDtoList, userDetails.getId()));
	}

	@PostMapping("/picture-generate-responses/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PictureGenerateResponseSubmitDto>> submitPictureGenerateResponse(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.submitToAdmin(userDetails.getId(), pictureGenerateResponseId));
	}

	@PostMapping("/picture-generate-responses/{pictureGenerateResponseId}/memo")
	public ResponseEntity<ApiResult<Boolean>> updateMemo(
		@PathVariable Long pictureGenerateResponseId,
		@RequestBody UpdateMemoRequestDto updateMemoRequestDto) {
		return success(pictureGenerateWorkService.updateMemo(pictureGenerateResponseId, updateMemoRequestDto));
	}
}