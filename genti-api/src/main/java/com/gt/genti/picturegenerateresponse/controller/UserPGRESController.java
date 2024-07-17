package com.gt.genti.picturegenerateresponse.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.error.ResponseCode;
import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.swagger.EnumResponse;
import com.gt.genti.swagger.EnumResponses;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[UserPGRESController] 유저의 요청에 의한 사진생성응답", description = "사진 생성 응답을 조회합니다.")
@RestController
@RequestMapping("/api/v1/users/picture-generate-responses")
@RequiredArgsConstructor
public class UserPGRESController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Operation(summary = "완성된 사진 확인(사진에 문제없음)처리", description = "완성된 사진을 최종 확인처리한다.")
	@EnumResponses(value = {
		@EnumResponse(ResponseCode.OK)
	})
	@Logging(item = LogItem.PGRES_VERIFY, action = LogAction.COMPLETE, requester = LogRequester.USER)
	@PostMapping("/{pictureGenerateResponseId}/verify")
	public ResponseEntity<ApiResult<Boolean>> verifyCompletedPGREQ(
		@AuthUser Long userId,
		@Parameter(example = "1", description = "사용자가 확인완료처리하고싶은 최종 사진이 포함된 '사진생성응답'의 id 값")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId) {
		return GentiResponse.success(pictureGenerateWorkService.verifyPGRES(userId,
			pictureGenerateResponseId));
	}
}
