package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(Long userId);

	public PGREQDetailFindByUserResponseDto findActivePGREQByUser(Long userId);
	public Boolean isActivePGREQExists(Long userId);

	public PGREQDetailFindByUserResponseDto findPGREQByUserAndId(Long userId, Long id);

	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(Long userId);

	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(Long requesterId,
		PGREQSaveRequestDto PGREQSaveRequestDto);

	@Transactional
	public void modifyPictureGenerateRequest(Long userId,
		PGREQUpdateRequestDto PGREQUpdateRequestDto);

}
