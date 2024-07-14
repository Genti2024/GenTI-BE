package com.gt.genti.responseexample.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.responseexample.dto.response.ExampleWithPictureFindResponseDto;
import com.gt.genti.responseexample.service.ResponseExampleService;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserExampleController] 유저 사진생성 예시 컨트롤러", description = "사진생성 예시를 조회합니다.")
@RestController
@RequestMapping("/api/v1/users/examples")
@RequiredArgsConstructor
public class UserExampleController {
	private final ResponseExampleService responseExampleService;

	@Logging(item = LogItem.RESPONSE_EXAMPLE, action = LogAction.VIEW, requester = LogRequester.USER)
	@Operation(summary = "예시 전체조회", description = "예시 사진&프롬프트를 전체 조회합니다." + "<br/>"
		+ "한번에 모두 조회해서 random하게 렌더링한다고 가정하고 pagination으로 안했습니다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@GetMapping("/with-picture")
	public ResponseEntity<ApiResult<List<ExampleWithPictureFindResponseDto>>> getAllResponseExamples() {
		return success(responseExampleService.getAllResponseExamples());
	}

}
