package com.gt.genti.service;

import org.springframework.stereotype.Service;

import com.gt.genti.domain.PictureCreateRequest;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;
import com.gt.genti.repository.PictureCreateRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AService {
	private final PictureCreateRequestRepository pictureCreateRequestRepository;

	public PictureGenerateRequestResponseDto getRequest(Long id) {
		PictureCreateRequest pictureCreateRequest = pictureCreateRequestRepository.findById(id).orElseThrow(
			RuntimeException::new);
		return new PictureGenerateRequestResponseDto(pictureCreateRequest);
	}
}
