package com.gt.genti.adapter.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.command.user.PGREQSaveCommand;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;
import com.gt.genti.dto.admin.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.dto.user.request.PGREQSaveRequestDto;
import com.gt.genti.dto.user.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.dto.user.response.PGREQStatusResponseDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(User user);

	public PGREQStatusResponseDto getPGREQStatusIfPendingExists(User user);

	public PGREQDetailFindByUserResponseDto findPGREQByRequestAndId(User user, Long id);

	public PictureGenerateRequest createPGREQ(User requester,
		PGREQSaveCommand pgreqSaveCommand);

	public void modifyPGREQ(User user,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto);

	PGREQBriefFindByUserResponseDto getByRequesterAndStatusIs(User requester, PictureGenerateRequestStatus status);

	Boolean verifyCompletedPGREQ(User requester, Long pictureGenerateRequestId);

	Page<PGREQDetailFindByAdminResponseDto> getAllByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);

	Page<PGREQDetailFindByAdminResponseDto> getAllByPGRESStatusInAndMatchToAdminIs(List<PictureGenerateResponseStatus> pictureGenerateResponseStatuses, boolean b, Pageable pageable);
}
