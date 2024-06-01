package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateWorkService;
import com.gt.genti.dto.PGRESUpdateByAdminResponseDto;
import com.gt.genti.dto.PictureUrlUpdateRequestDto;
import com.gt.genti.other.auth.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/picture-generate-responses")
@RequiredArgsConstructor
public class AdminPGRESController {
	private final PictureGenerateWorkService pictureGenerateWorkService;

	@PostMapping("/{pictureGenerateResponseId}/submit")
	public ResponseEntity<ApiResult<PGRESUpdateByAdminResponseDto>> submit(
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.submitFinal(pictureGenerateResponseId));
	}

	@PostMapping("/{pictureGenerateResponseId}/pictures")
	public ResponseEntity<ApiResult<Boolean>> updatePictureList(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody List<PictureUrlUpdateRequestDto> reuqestDtoList,
		@PathVariable Long pictureGenerateResponseId) {
		return success(pictureGenerateWorkService.updatePictureListCreatedByAdmin(userDetails.getId(), reuqestDtoList,
			pictureGenerateResponseId));
	}
}
