package com.gt.genti.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;

public interface PictureGenerateRequestUseCase {
	public List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(Long userId);

	public PGREQStatusResponseDto getPGREQStatusIfPendingExists(Long userId);

	public PGREQDetailFindByUserResponseDto findPGREQByRequestAndId(Long userId, Long id);

	public PictureGenerateRequest createPGREQ(Long userId,
		PGREQSaveCommand pgreqSaveCommand);

	public void modifyPGREQ(Long userId,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto);

	PGREQBriefFindByUserResponseDto getByRequesterAndStatusIs(Long userId, PictureGenerateRequestStatus status);

	Boolean verifyCompletedPGREQ(Long userId, Long pictureGenerateRequestId);

	Page<PGREQDetailFindByAdminResponseDto> getAllByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);

	Page<PGREQDetailFindByAdminResponseDto> getAllByPGRESStatusInAndMatchToAdminIs(
		List<PictureGenerateResponseStatus> pictureGenerateResponseStatuses, boolean b, Pageable pageable);
}
