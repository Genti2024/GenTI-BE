package com.gt.genti.picture.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picture.api.PictureApi;
import com.gt.genti.picture.service.UploadUrlService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/presigned-url")
@RequiredArgsConstructor
public class PictureController implements PictureApi {
	private final UploadUrlService uploadUrlService;

	@Logging(item = LogItem.PICTURE_PRESIGNED_URL, action = LogAction.GET, requester = LogRequester.ANONYMOUS)
	@PostMapping
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(
		@RequestBody @Valid PreSignedUrlRequestDto preSignedUrlRequestDto) {
		return GentiResponse.success(uploadUrlService.getUploadUrl(preSignedUrlRequestDto));
	}

	@Logging(item = LogItem.PICTURE_PRESIGNED_URL, action = LogAction.GET, requester = LogRequester.ANONYMOUS)
	@PostMapping("/many")
	public ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(
		@RequestBody @Valid List<PreSignedUrlRequestDto> requestDtoList) {
		return GentiResponse.success(uploadUrlService.getUploadUrls(requestDtoList));
	}
}
