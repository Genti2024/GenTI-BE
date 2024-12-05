package com.gt.genti.usecase;

import java.util.List;

import com.gt.genti.picturegeneraterequest.command.AdvancedPGREQSaveCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQAdminMatchedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQCreatorSubmittedDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.service.PictureGenerateRequestCancellationReason;
import com.gt.genti.picturegenerateresponse.service.mapper.PictureGenerateResponseStatusForAdmin;

public interface PictureGenerateRequestUseCase {
	PGREQStatusResponseDto getPendingPGREQStatusIfExists(Long userId);

	PictureGenerateRequest createPGREQ(Long userId,
		PGREQSaveCommand pgreqSaveCommand);

	PictureGenerateRequest createPaidPGREQForOne(Long userId,
		 PGREQSaveCommand pgreqSaveCommand);

	PictureGenerateRequest createPaidPGREQForTwo(Long userId,
	   AdvancedPGREQSaveCommand advancedPGREQSaveCommand);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatched(Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByPGRESStatus(
		PictureGenerateResponseStatusForAdmin statusForAdmin, Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllAdminMatchedByRequesterEmail(String email,
		Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllPaidAdminMatched(Pageable pageable);

	Page<PGREQAdminMatchedDetailFindByAdminResponseDto> getAllPaidAdminMatchedByRequesterEmail(String email, Pageable pageable);

	boolean cancelRequestByAdmin(Long pictureGenerateRequestId);

	void cancelRequest(PictureGenerateRequest request, PictureGenerateRequestCancellationReason reason);

	void cancelAllRequests(List<PictureGenerateRequest> request, PictureGenerateRequestCancellationReason reason);

	Boolean confirmCanceledPGREQ(Long userId, Long pictureGenerateRequestId);
}
