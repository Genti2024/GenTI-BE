package com.gt.genti.picturegenerateresponse.controller;

import static com.gt.genti.response.GentiResponse.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.model.LogAction;
import com.gt.genti.model.LogItem;
import com.gt.genti.model.LogRequester;
import com.gt.genti.model.Logging;
import com.gt.genti.picture.dto.request.CommonPictureKeyUpdateRequestDto;
import com.gt.genti.picture.dto.response.CommonPictureResponseDto;
import com.gt.genti.picturegenerateresponse.api.AdminPGRESApi;
import com.gt.genti.picturegenerateresponse.dto.request.PGRESUpdateAdminInChargeRequestDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESSubmitByAdminResponseDto;
import com.gt.genti.picturegenerateresponse.dto.response.PGRESUpdateAdminInChargeResponseDto;
import com.gt.genti.picturegenerateresponse.service.PictureGenerateWorkService;
import com.gt.genti.user.model.AuthUser;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/picture-generate-responses")
@RequiredArgsConstructor
public class AdminPGRESController implements AdminPGRESApi {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@Logging(item = LogItem.PGRES, action = LogAction.COMPLETE, requester = LogRequester.ADMIN)
	@PostMapping("/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PGRESSubmitByAdminResponseDto>> submit(
		@Parameter(description = "사진생성응답 Id", example = "1", required = true)
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.submitFinal(pictureGenerateResponseId));
	}

	@Logging(item = LogItem.PGRES_PICTURE_COMPLETED, action = LogAction.UPDATE, requester = LogRequester.ADMIN)
	@PostMapping("/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<List<CommonPictureResponseDto>>> updatePictureList(
		@AuthUser Long userId,
		@Parameter(description = "업로드한 사진 url 리스트", required = true)
		@RequestBody @Valid List<@Valid CommonPictureKeyUpdateRequestDto> reuqestDtoList,
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId) {
		return success(
			pictureGenerateWorkService.updatePictureListCreatedByAdmin(userId, reuqestDtoList,
				pictureGenerateResponseId));
	}

	@Logging(item = LogItem.PGRES_ASSIGNEE, action = LogAction.UPDATE, requester = LogRequester.ADMIN)
	@PostMapping("/{pictureGenerateResponseId}/admin-in-charge")
	public ResponseEntity<ApiResult<PGRESUpdateAdminInChargeResponseDto>> updateAdminInCharge(
		@PathVariable(value = "pictureGenerateResponseId") Long pictureGenerateResponseId,
		@RequestBody PGRESUpdateAdminInChargeRequestDto requestDto) {
		return success(pictureGenerateWorkService.updateAdminInCharge(pictureGenerateResponseId, requestDto));
	}
}
