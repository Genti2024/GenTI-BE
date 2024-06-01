package com.gt.genti.adapter.in.web.admin;

import static com.gt.genti.other.util.ApiUtils.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gt.genti.application.service.PictureGenerateRequestService;
import com.gt.genti.dto.PGREQDetailFindResponseDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/picture-generate-requests")
@RequiredArgsConstructor
public class AdminPGREQController {
	private final PictureGenerateRequestService pictureGenerateRequestService;

	@GetMapping("/all")
	public ResponseEntity<ApiResult<Page<PGREQDetailFindResponseDto>>> getAllPictureGenerateRequest(
		@RequestParam @NotNull int page,
		@RequestParam @NotNull @Min(1) int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		return success(pictureGenerateRequestService.getAllPictureGenerateRequest(pageable));
	}

}
