package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQDetailFindByUserResponseDto> getAllPictureGenerateRequestForUser(User user);

	public PGREQDetailFindByUserResponseDto findActivePGREQByUser(User user);
	public Boolean isActivePGREQExists(User user);

	public PGREQDetailFindByUserResponseDto findPGREQByUserAndId(User user, Long id);

	public List<PGREQBriefFindByUserResponseDto> getAllMyPictureGenerateRequests(User user);

	@Transactional
	public PictureGenerateRequest createPictureGenerateRequest(User requester,
		PGREQSaveRequestDto PGREQSaveRequestDto);

	@Transactional
	public void modifyPictureGenerateRequest(User user,
		PGREQUpdateRequestDto PGREQUpdateRequestDto);

}
