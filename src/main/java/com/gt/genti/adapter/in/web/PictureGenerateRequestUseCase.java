package com.gt.genti.adapter.in.web;

import java.util.List;

import com.gt.genti.domain.User;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;
import com.gt.genti.dto.PictureGenerateRequestResponseDto;
import com.gt.genti.dto.PictureGenerateRequestSimplifiedResponseDto;

public interface PictureGenerateRequestUseCase {
	public List<PictureGenerateRequestDetailResponseDto> getMyActivePictureGenerateRequest(Long userId);

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id);

	public List<PictureGenerateRequestSimplifiedResponseDto> getAllMyPictureGenerateRequests(User requester);

	public PictureGenerateRequestResponseDto createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto);

	public Boolean modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto);

}
