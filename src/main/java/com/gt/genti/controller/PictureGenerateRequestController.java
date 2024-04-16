package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.aop.CheckUserIsQuit;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.security.PrincipalDetail;
import com.gt.genti.service.PictureGenerateRequestService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class PictureGenerateRequestController {
	private final PictureGenerateRequestService pictureGenerateRequestService;

	@CheckUserIsQuit
	@GetMapping("/active")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDto>>> getMyActivePictureGenerateRequest(
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return success(
			pictureGenerateRequestService.getMyActivePictureGenerateRequest(principalDetail.getUser().getId()));
	}

	@CheckUserIsQuit
	@PostMapping("")
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthenticationPrincipal PrincipalDetail principalDetail,
		@RequestBody PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {

		return success(
			pictureGenerateRequestService.createPictureGenerateRequest(principalDetail.getUser(),
				pictureGenerateRequestRequestDto));
	}

	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal PrincipalDetail principalDetail,
		@RequestBody PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto) {
		return success(
			pictureGenerateRequestService.modifyPictureGenerateRequest(principalDetail.getUser().getId(),
				pictureGenerateRequestModifyDto));
	}

}
