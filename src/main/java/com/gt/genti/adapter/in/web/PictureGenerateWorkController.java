package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.other.config.auth.UserDetailsImpl;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.application.service.PictureGenerateWorkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class PictureGenerateWorkController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@GetMapping("")
	public ResponseEntity<ApiResult<PictureGenerateRequestBriefResponseDto>> getMyAssignedPictureGenerateRequest(
		@AuthenticationPrincipal
		UserDetailsImpl userDetails) {
		return success(pictureGenerateWorkService.getCreatorAssignedPictureGenerateRequestBrief(
			userDetails.getId()));
	}
}
