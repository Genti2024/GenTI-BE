package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;

public interface PictureGenerateRequestUseCase {
	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequest(Long userId,
		PictureGenerateRequestStatus status);

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequest(Long userId);

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id);

	public List<PictureGenerateRequestBriefResponseDto> getAllMyPictureGenerateRequests(Long userId);

	@Transactional
	public Boolean createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto);

	@Transactional
	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto);

}
