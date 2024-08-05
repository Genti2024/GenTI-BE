package com.gt.genti.picturegenerateresponse.api;

import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@AuthorizedUser
@Tag(name = "[UserPGRESController] 유저의 요청에 의한 사진생성응답", description = "사진 생성 응답을 조회합니다.")
public interface UserPGRESApi {
	@Operation(summary = "완성된 사진 확인(사진에 문제없음)처리", description = "완성된 사진을 최종 확인처리한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<Boolean>> verifyCompletedPGREQ(
		@AuthUser Long userId,
		@Parameter(example = "1", description = "사용자가 확인완료처리하고싶은 최종 사진이 포함된 '사진생성응답'의 id 값")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId);

	@Operation(summary = "별점 주기", description = "생성 완료된 사진에 별점 주기")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK),
		@EnumResponse(ResponseCode.PictureGenerateResponseNotFound)
	})
	ResponseEntity<GentiResponse.ApiResult<Boolean>> ratePicture(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@Parameter(example = "3", description = "생성 완료된 사진에 대한 별점 (값의 범위 : 1 ~ 5)")
		@RequestParam(name = "star") @NotNull @Range(min = 1, max = 5) Integer star);
}
