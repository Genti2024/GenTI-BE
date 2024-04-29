package com.gt.genti.external.aws.controller;

import static com.gt.genti.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.genti.config.auth.UserDetailsImpl;
import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.external.aws.service.S3Service;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Service s3Service;

	@PostMapping("/presigned-url")
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getPreSignedUrl(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody PreSignedUrlRequestDto preSignedUrlRequestDto) {
		return success(s3Service.getPreSignedUrl(userDetails.getId(), preSignedUrlRequestDto));
	}
}
