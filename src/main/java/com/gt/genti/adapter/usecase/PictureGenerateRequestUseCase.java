package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.dto.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.PGREQDetailFindResponseDto;
import com.gt.genti.dto.PGREQUpdateRequestDto;
import com.gt.genti.dto.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.PGREQSaveRequestDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(Long userId);

	public PGREQDetailFindByUserResponseDto getPictureGenerateRequestForUser(Long userId);

	public PGREQDetailFindResponseDto getPictureGenerateRequestById(Long id);

	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(Long userId);

	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(Long requesterId,
		PGREQSaveRequestDto PGREQSaveRequestDto);

	@Transactional
	public void modifyPictureGenerateRequest(Long userId,
		PGREQUpdateRequestDto PGREQUpdateRequestDto);

}
