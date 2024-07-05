package com.gt.genti.application.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.domain.enums.PictureGenerateResponseStatus;

public interface PictureGenerateRequestPort {

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findById(Long id);

	Optional<PictureGenerateRequest> findByIdAndRequester(Long id, User requester);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);

	Optional<PictureGenerateRequest> findByRequesterAndStatusInOrderByCreatedByDesc(User requester,
		List<PictureGenerateRequestStatus> statusList);

	Page<PictureGenerateResponse> findByPGRESStatusInAndMatchToAdminIs(List<PictureGenerateResponseStatus> statusList, boolean matchToAdmin, Pageable pageable);
	Page<PictureGenerateRequest> findAll(Pageable pageable);

	Page<PictureGenerateRequest> findByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);
}
