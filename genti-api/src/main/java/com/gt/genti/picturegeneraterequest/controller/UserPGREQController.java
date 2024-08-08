package com.gt.genti.picturegeneraterequest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picturegeneraterequest.api.UserPGREQApi;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.response.GentiResponse;
import com.gt.genti.response.GentiResponse.ApiResult;
import com.gt.genti.usecase.PictureGenerateRequestUseCase;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/picture-generate-requests")
@RequiredArgsConstructor
public class UserPGREQController implements UserPGREQApi {
	private final PictureGenerateRequestUseCase pictureGenerateRequestUseCase;

	@Deprecated
	@Logging(item = LogItem.PGREQ, action = LogAction.VIEW, requester = LogRequester.USER)
	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PGREQBriefFindByUserResponseDto>>> getAllUsersPictureGenerateRequest(
		@AuthUser Long userId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.findAllPGREQByRequester(userId));
	}

	@Logging(item = LogItem.PGREQ_INPROGESS, action = LogAction.SEARCH, requester = LogRequester.USER)
	@GetMapping("/pending")
	public ResponseEntity<ApiResult<PGREQStatusResponseDto>> getPendingPGRESStatus(
		@AuthUser Long userId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.getPendingPGREQStatusIfExists(userId));
	}

	@Logging(item = LogItem.PGREQ_CANCEL_VERIFY, action = LogAction.UPDATE, requester = LogRequester.USER)
	@GetMapping("/{pictureGenerateRequestId}/confirm-cancel-status")
	public ResponseEntity<ApiResult<Boolean>> confirmCanceledPGREQ(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateRequestId")
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId
	) {
		return GentiResponse.success(
			pictureGenerateRequestUseCase.confirmCanceledPGREQ(userId, pictureGenerateRequestId));
	}

	@Logging(item = LogItem.PGREQ, action = LogAction.REQUEST, requester = LogRequester.USER)
	@PostMapping
	public ResponseEntity<ApiResult<Boolean>> createPictureGenerateRequest(
		@AuthUser Long userId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.createPGREQ(userId,
			pgreqSaveRequestDto.toCommand());
		return GentiResponse.success(true);
	}

	@Deprecated
	@Logging(item = LogItem.PGREQ, action = LogAction.UPDATE, requester = LogRequester.USER)
	@PutMapping("/{pictureGenerateRequestId}")
	public ResponseEntity<ApiResult<Boolean>> modifyPictureGenerateRequest(
		@AuthUser Long userId,
		@PathVariable(value = "pictureGenerateRequestId")
		@Schema(description = "사진생성요청id", example = "1")
		Long pictureGenerateRequestId,
		@RequestBody @Valid PGREQSaveRequestDto pgreqSaveRequestDto) {
		pictureGenerateRequestUseCase.modifyPGREQ(userId, pictureGenerateRequestId,
			pgreqSaveRequestDto);
		return GentiResponse.success(true);
	}
}
