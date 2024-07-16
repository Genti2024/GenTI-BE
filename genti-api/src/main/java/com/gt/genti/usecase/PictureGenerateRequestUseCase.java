package com.gt.genti.usecase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.command.PGREQSaveCommand;
import com.gt.genti.picturegeneraterequest.dto.request.PGREQSaveRequestDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQBriefFindByUserResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.dto.response.PGREQStatusResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

public interface PictureGenerateRequestUseCase {
	List<PGREQBriefFindByUserResponseDto> findAllPGREQByRequester(Long userId);

	PGREQStatusResponseDto getPendingPGREQStatusIfExists(Long userId);


	PictureGenerateRequest createPGREQ(Long userId,
		PGREQSaveCommand pgreqSaveCommand);

	void modifyPGREQ(Long userId,
		Long pictureGenerateRequestId, PGREQSaveRequestDto pgreqSaveRequestDto);

	Page<PGREQDetailFindByAdminResponseDto> getAllByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);

	Page<PGREQDetailFindByAdminResponseDto> getAllByPGRESStatusInAndMatchToAdminIs(
		List<PictureGenerateResponseStatus> pictureGenerateResponseStatuses, boolean b, Pageable pageable);
}
