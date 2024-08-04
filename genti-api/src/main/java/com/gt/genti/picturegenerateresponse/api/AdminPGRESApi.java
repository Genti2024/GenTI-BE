package com.gt.genti.picturegenerateresponse.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.dto.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedAdmin;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.swagger.RequireImageUpload;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@AuthorizedAdmin
@Tag(name = "[AdminPGRESController] 어드민 사진생성응답 컨트롤러", description = "사진생성응답을 조회, 수정")
public interface AdminPGRESApi {
	@Operation(summary = "사진생성응답 최종 제출", description = "사진생성응답을 최종 제출합니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<PGRESSubmitByAdminResponseDto>> submit(
		@Parameter(description = "사진생성응답 Id", example = "1", required = true)
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId);

	@Operation(summary = "응답에 최종 사진 리스트 추가", description = "사진생성응답에 최종 사진 리스트 추가")
	@RequireImageUpload
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<GentiResponse.ApiResult<List<CommonPictureResponseDto>>> updatePictureList(
		@AuthUser Long userId,
		@Parameter(description = "업로드한 사진 url 리스트", required = true)
		@RequestBody @Valid List<@Valid CommonPictureKeyUpdateRequestDto> reuqestDtoList,
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId);

	@Operation(summary = "담당자(어드민) 설정", description = "사진생성응답 담당 어드민 설정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<GentiResponse.ApiResult<PGRESUpdateAdminInChargeResponseDto>> updateAdminInCharge(
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@RequestBody PGRESUpdateAdminInChargeRequestDto requestDto);
}
