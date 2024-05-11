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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PostService;
import com.gt.genti.application.service.UploadUrlService;
import com.gt.genti.dto.PostBriefResponseDto;
import com.gt.genti.dto.PostDetailResponseDto;
import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.other.annotation.ToBeUpdated;
import com.gt.genti.other.aop.annotation.CheckUserIsQuit;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadUrlController {
	private final UploadUrlService uploadUrlService;

	@PostMapping("/presigned-url")
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(@RequestBody PreSignedUrlRequestDto preSignedUrlRequestDto){
		return success(uploadUrlService.getUploadUrl(preSignedUrlRequestDto));
	}

	@PostMapping("/presigned-urls")
	public ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(@RequestBody List<PreSignedUrlRequestDto> requestDtoList){
		return success(uploadUrlService.getUploadUrls(requestDtoList));
	}
}
