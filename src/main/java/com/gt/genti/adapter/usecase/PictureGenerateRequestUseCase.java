package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.User;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;
import com.gt.genti.dto.PictureGenerateRequestSimplifiedResponseDto;

public interface PictureGenerateRequestUseCase {
	public List<PictureGenerateRequestDetailResponseDto> getPictureGenerateRequestByUserId(Long userId);

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id);

	public List<PictureGenerateRequestSimplifiedResponseDto> getAllMyPictureGenerateRequests(User requester);

	@Transactional
	public PictureGenerateRequestResponseDto createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto);

	@Transactional
	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto);

}
