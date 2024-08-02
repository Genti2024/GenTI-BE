package com.gt.genti.responseexample.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.swagger.AuthorizedUser;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@AuthorizedUser
@Tag(name = "[UserExampleController] 유저 사진생성 예시 컨트롤러", description = "사진생성 예시를 조회합니다.")
public interface UserResponseExampleApi {

	@Operation(summary = "예시 전체조회", description = "예시 사진&프롬프트를 전체 조회합니다." + "<br/>"
		+ "한번에 모두 조회해서 random하게 렌더링한다고 가정하고 pagination으로 안했습니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples();
}
