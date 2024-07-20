package com.gt.genti.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQCreatorSubmittedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;

public interface PictureGenerateRequestUseCase {
	List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(Long userId);

	PGREQStatusResponseDto getPendingPGREQStatusIfExists(Long userId);

	PictureGenerateRequest createPGREQ(Long userId,
		PGREQSaveCommand pgreqSaveCommand);

	void modifyPGREQ(Long userId,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatched(Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByPGRESStatus(
		PictureGenerateResponseStatusForAdmin statusForAdmin, Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByRequesterEmail(String email,
		Pageable pageable);

	Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmitted(Pageable pageable);

	Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmittedByPGRESStatus(
		PictureGenerateResponseStatusForAdmin statusForAdmin, Pageable pageable);

	Page<PGREQCreatorSubmittedDetailFindByAdminResponseDto> getAllCreatorSubmittedByRequesterEmail(String email,
		Pageable pageable);
}
