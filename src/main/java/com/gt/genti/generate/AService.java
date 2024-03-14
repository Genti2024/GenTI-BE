package com.gt.genti.generate;

import org.springframework.stereotype.Service;

import com.gt.genti.generate.domain.PictureGenerateRequest;
import com.gt.genti.generate.domain.PictureGenerateRequestRepository;
import com.gt.genti.generate.dto.PictureGenerateRequestResponseDto;

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
