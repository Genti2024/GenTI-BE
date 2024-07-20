package com.gt.genti.picture.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picture.service.UploadUrlService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[PictureController] 공통 사진 컨트롤러", description = "사진 업로드 url을 요청합니다.")
@RestController
@RequestMapping("/api/v1/presigned-url")
@RequiredArgsConstructor
public class PictureController {
	private final UploadUrlService uploadUrlService;

	@Operation(summary = "사진 업로드 url 1개 요청", description = "사진 1개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PICTURE_PRESIGNED_URL, action = LogAction.GET, requester = LogRequester.ANONYMOUS)
	@PostMapping
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(
		@RequestBody @Valid PreSignedUrlRequestDto preSignedUrlRequestDto) {
		return GentiResponse.success(uploadUrlService.getUploadUrl(preSignedUrlRequestDto));
	}

	@Operation(summary = "사진 업로드 url 리스트 요청", description = "사진 여러개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PICTURE_PRESIGNED_URL, action = LogAction.GET, requester = LogRequester.ANONYMOUS)
	@PostMapping("/many")
	public ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(
		@RequestBody @Valid List<PreSignedUrlRequestDto> requestDtoList) {
		return GentiResponse.success(uploadUrlService.getUploadUrls(requestDtoList));
	}
}
