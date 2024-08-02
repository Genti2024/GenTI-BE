package com.gt.genti.picture.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.gt.genti.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.Authorized;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Authorized
@Tag(name = "[PictureController] 공통 사진 컨트롤러", description = "사진 업로드 url을 요청합니다.")
public interface PictureApi {

	@Operation(summary = "사진 업로드 url 1개 요청", description = "사진 1개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<PreSignedUrlResponseDto>> getUploadUrl(
		@RequestBody @Valid PreSignedUrlRequestDto preSignedUrlRequestDto);

	@Operation(summary = "사진 업로드 url 리스트 요청", description = "사진 여러개의 업로드 url 요청")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<List<PreSignedUrlResponseDto>>> getUploadUrls(
		@RequestBody @Valid List<PreSignedUrlRequestDto> requestDtoList);
}
