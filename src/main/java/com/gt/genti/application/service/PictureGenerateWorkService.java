package com.gt.genti.application.service;

import org.springframework.stereotype.Service;

import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.repository.PictureGenerateRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureGenerateWorkService {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	public PictureGenerateRequestBriefResponseDto getCreatorAssignedPictureGenerateRequestBrief(Long creatorId) {
		return pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
			creatorId).map(
			(pictureGenerateRequest) ->
				PictureGenerateRequestBriefResponseDto.builder()
					.requestId(pictureGenerateRequest.getId())
					.cameraAngle(pictureGenerateRequest.getCameraAngle().getStringValue())
					.shotCoverage(pictureGenerateRequest.getShotCoverage().getStringValue())
					.prompt(pictureGenerateRequest.getPrompt())
					.build()
		).orElseThrow(() -> new RuntimeException("배정된 요청 없음 나중에 변경"));
	}
}

