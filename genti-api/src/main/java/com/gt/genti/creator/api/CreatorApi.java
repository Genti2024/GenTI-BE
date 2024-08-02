package com.gt.genti.creator.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.gt.genti.creator.dto.request.AccountUpdateRequestDto;
import com.gt.genti.creator.dto.request.CreatorStatusUpdateRequestDto;
import com.gt.genti.creator.dto.response.CreatorFindResponseDto;
import com.gt.genti.creator.dto.response.CreatorStatusUpdateResponseDto;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedCreator;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "[CreatorController] 공급자 정보 컨트롤러", description = "공급자의 정보를 조회,수정합니다.")
@AuthorizedCreator
public interface CreatorApi {

	@Operation(summary = "공급자 내정보 보기", description = "공급자의 내정보 보기")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)
	})
	ResponseEntity<ApiResult<CreatorFindResponseDto>> getCreatorInfo(
		@AuthUser Long userId);

	@Operation(summary = "공급자 계좌정보 수정", description = "공급자의 내 계좌정보 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)
	})
	ResponseEntity<ApiResult<Boolean>> updateAccountInfo(
		@AuthUser Long userId,
		@RequestBody @Valid AccountUpdateRequestDto accountUpdateRequestDto);

	@Operation(summary = "공급자 작업가능상태 수정", description = "공급자의 내 작업가능상태 수정")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.CreatorNotFound)
	})
	ResponseEntity<ApiResult<CreatorStatusUpdateResponseDto>> updateCreatorStatus(
		@AuthUser Long userId,
		@RequestBody @Valid CreatorStatusUpdateRequestDto creatorStatusUpdateRequestDto);
}
