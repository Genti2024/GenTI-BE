package com.gt.genti.adapter.in.web;

import static com.gt.genti.other.util.ApiUtils.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateRequestService;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/picture-generate-requests")
@RequiredArgsConstructor
public class AdminPictureGenerateRequestController {
	private final PictureGenerateRequestService pictureGenerateRequestService;

	@GetMapping("/all")
	public ResponseEntity<ApiResult<List<PictureGenerateRequestDetailResponseDto>>> getAllPictureGenerateRequest() {
		return success(pictureGenerateRequestService.getAllPictureGenerateRequest());
	}


}
