package com.gt.genti.picturegenerateresponse.controller;

import static com.gt.genti.response.GentiResponse.*;

import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegenerateresponse.api.UserPGRESApi;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/picture-generate-responses")
@RequiredArgsConstructor
public class UserPGRESController implements UserPGRESApi {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Logging(item = LogItem.PGRES_VERIFY, action = LogAction.COMPLETE, requester = LogRequester.USER)
	@PostMapping("/{pictureGenerateResponseId}/verify")
	public ResponseEntity<ApiResult<Boolean>> verifyCompletedPGREQ(
		@AuthUser Long userId,
		@Parameter(example = "1", description = "사용자가 확인완료처리하고싶은 최종 사진이 포함된 '사진생성응답'의 id 값")
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId) {
		return GentiResponse.success(pictureGenerateWorkService.verifyPGRES(userId,
			pictureGenerateResponseId));
	}

	@Logging(item = LogItem.PGRES_STAR, action = LogAction.CREATE, requester = LogRequester.USER)
	@PostMapping("/{pictureGenerateResponseId}/rate")
	public ResponseEntity<ApiResult<Boolean>> ratePicture(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@Parameter(example = "3", description = "생성 완료된 사진에 대한 별점 (값의 범위 : 1 ~ 5)")
		@RequestParam(name = "star") @NotNull @Range(min = 1, max = 5) Integer star) {
		return GentiResponse.success(pictureGenerateWorkService.ratePicture(userId, pictureGenerateResponseId, star));
	}
}
