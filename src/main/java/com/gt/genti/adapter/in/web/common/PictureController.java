package com.gt.genti.adapter.in.web.common;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.UploadUrlService;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.other.swagger.EnumResponse;
import com.gt.genti.other.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "[PictureController] 공통 사진 컨트롤러", description = "사진 업로드 url을 요청합니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureController {
	private final UploadUrlService uploadUrlService;

	@Operation(summary = "사진 업로드 url 1개 요청", description = "사진 1개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/presigned-url")
	public ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(
		@RequestBody @Valid PreSignedUrlRequestDto preSignedUrlRequestDto) {
		return success(uploadUrlService.getUploadUrl(preSignedUrlRequestDto));
	}

	@Operation(summary = "사진 업로드 url 리스트 요청", description = "사진 여러개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@PostMapping("/presigned-url/many")
	public ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(
		@RequestBody @Valid List<PreSignedUrlRequestDto> requestDtoList) {
		return success(uploadUrlService.getUploadUrls(requestDtoList));
	}
}
