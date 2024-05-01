package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.config.auth.UserDetailsImpl;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;
import com.gt.genti.application.service.PictureGenerateRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class PictureGenerateRequestController {
	private final PictureGenerateRequestService pictureGenerateRequestService;

	@CheckUserIsQuit
	@GetMapping("/active")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDto>>> getMyActivePictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return success(
			pictureGenerateRequestService.getMyActivePictureGenerateRequest(userDetails.getId()));
	}

	@CheckUserIsQuit
	@PostMapping("")
	public ResponseEntity<ApiResult<PictureGenerateRequestResponseDto>> createPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto) {

		return success(
			pictureGenerateRequestService.createPictureGenerateRequest(userDetails.getId(),
				pictureGenerateRequestRequestDto));
	}

	@CheckUserIsQuit
	@PutMapping("")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto) {
		return success(
			pictureGenerateRequestService.modifyPictureGenerateRequest(userDetails.getId(),
				pictureGenerateRequestModifyDto));
	}

}
