package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.request.PGREQUpdateRequestDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(User user);

	public Boolean isPendingPGREQExists(User user);

	public PGREQDetailFindByUserResponseDto findPGREQByRequestAndId(User user, Long id);


	@Transactional
	public PictureGenerateRequest createPGREQ(User requester,
		PGREQSaveCommand pgreqSaveCommand);

	@Transactional
	public void modifyPGREQ(User user,
		PGREQUpdateRequestDto PGREQUpdateRequestDto);

	PGREQBriefFindByUserResponseDto findByRequestAndStatusIs(User requester, PictureGenerateRequestStatus status);
}
