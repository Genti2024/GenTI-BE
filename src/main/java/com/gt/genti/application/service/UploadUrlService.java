package com.gt.genti.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.external.aws.dto.PreSignedUrlRequestDto;
import com.gt.genti.external.aws.dto.PreSignedUrlResponseDto;
import com.gt.genti.external.aws.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadUrlService {
	private final S3Service s3Service;

	@Transactional
	public List<PreSignedUrlResponseDto> getUploadUrls(List<PreSignedUrlRequestDto> preSignedUrlRequestDto) {
		return s3Service.getPreSignedUrlMany(preSignedUrlRequestDto);
	}

	@Transactional
	public PreSignedUrlResponseDto getUploadUrl(PreSignedUrlRequestDto preSignedUrlRequestDto) {
		return s3Service.getPreSignedUrl(preSignedUrlRequestDto);
	}

}

