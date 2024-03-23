package com.gt.genti.service;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateRequestRepository;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AService {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	public PictureGenerateRequestResponseDto getRequest(Long id) {
		PictureGenerateRequest pictureGenerateRequest = pictureGenerateRequestRepository.findById(id).orElseThrow(
			RuntimeException::new);
		return new PictureGenerateRequestResponseDto(pictureGenerateRequest);
	}
}
