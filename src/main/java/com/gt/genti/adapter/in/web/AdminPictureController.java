package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.command.CreatePictureCompletedCommand;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.dto.UpdatePictureUrlRequestDto;
import com.gt.genti.error.ErrorCode;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.other.auth.UserDetailsImpl;
import com.gt.genti.repository.PictureGenerateResponseRepository;
import com.gt.genti.service.PictureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/picture-generate-responses")
@RequiredArgsConstructor
public class AdminPictureController {
	private final PictureService pictureService;
	private final PictureGenerateResponseRepository pictureGenerateResponseRepository;

	@PostMapping("/{responseId}")
	public ResponseEntity<ApiResult<Boolean>> updatePicture(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		UpdatePictureUrlRequestDto requestDto,
		@PathVariable Long responseId) {
		PictureGenerateResponse foundPictureGenerateResponse = pictureGenerateResponseRepository.findById(responseId)
			.orElseThrow(() -> new ExpectedException(
				ErrorCode.PictureGenerateResponseNotFound));

		CreatePictureCompletedCommand command = CreatePictureCompletedCommand.builder()
			.pictureGenerateResponse(foundPictureGenerateResponse)
			.url(requestDto.getUrl())
			.userId(userDetails.getId()).build();
		pictureService.updatePicture(command);
		return success(true);
	}
}
