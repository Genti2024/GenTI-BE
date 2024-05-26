package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.dto.PictureGenerateRequestBriefResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDto;
import com.gt.genti.dto.PictureGenerateRequestDetailResponseDtoForUser;
import com.gt.genti.dto.PictureGenerateRequestModifyDto;
import com.gt.genti.dto.PictureGenerateRequestRequestDto;

public interface PictureGenerateRequestUseCase {
	public List<PictureGenerateRequestDetailResponseDtoForUser> getAllPictureGenerateRequestForUser(Long userId);

	public PictureGenerateRequestDetailResponseDtoForUser getPictureGenerateRequestForUser(Long userId);

	public PictureGenerateRequestDetailResponseDto getPictureGenerateRequestById(Long id);

	public List<PictureGenerateRequestBriefResponseDto> getAllMyPictureGenerateRequests(Long userId);

	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(Long requesterId,
		PictureGenerateRequestRequestDto pictureGenerateRequestRequestDto);

	@Transactional
	public void modifyPictureGenerateRequest(Long userId,
		PictureGenerateRequestModifyDto pictureGenerateRequestModifyDto);

}
