package com.gt.genti.controller;

import static com.gt.genti.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.security.PrincipalDetail;
import com.gt.genti.service.PictureGenerateWorkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class PictureGenerateWorkController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@GetMapping("")
	public ResponseEntity<ApiResult<PictureGenerateRequestBriefResponseDto>> getMyAssignedPictureGenerateRequest(
		@AuthenticationPrincipal
		PrincipalDetail principalDetail) {
		return success(pictureGenerateWorkService.getCreatorAssignedPictureGenerateRequestBrief(
			principalDetail.getUser().getId()));
	}
}
