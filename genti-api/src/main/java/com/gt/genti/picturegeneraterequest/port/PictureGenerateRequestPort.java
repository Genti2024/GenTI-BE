package com.gt.genti.picturegeneraterequest.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.dto.response.PGREQDetailFindByAdminResponseDto;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;

public interface PictureGenerateRequestPort {

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findById(Long id);

	Optional<PictureGenerateRequest> findByIdAndRequester(Long id, User requester);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);

	Optional<PictureGenerateRequest> findByUserAndStatusInOrderByCreatedByDesc(User user,
		List<PictureGenerateRequestStatus> statusList);

	Page<PictureGenerateResponse> findByPGRESStatusInAndMatchToAdminIs(List<PictureGenerateResponseStatus> statusList,
		boolean matchToAdmin, Pageable pageable);

	Page<PictureGenerateRequest> findAll(Pageable pageable);

	Page<PictureGenerateRequest> findByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);

	Page<PGREQDetailFindByAdminResponseDto> findAllByRequester(User foundUser, Pageable pageable);
}
