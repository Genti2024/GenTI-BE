package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.UploadUrlService;
import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureController {
	private final UploadUrlService uploadUrlService;

	@PostMapping("/presigned-url")
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(@RequestBody PreSignedUrlRequestDto preSignedUrlRequestDto){
		return success(uploadUrlService.getUploadUrl(preSignedUrlRequestDto));
	}

	@PostMapping("/presigned-url/many")
	public ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(@RequestBody List<PreSignedUrlRequestDto> requestDtoList){
		return success(uploadUrlService.getUploadUrls(requestDtoList));
	}
}
