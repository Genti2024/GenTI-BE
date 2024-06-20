package com.gt.genti.application.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

public interface PictureGenerateRequestPort {

	List<PictureGenerateRequest> findByRequestStatusAndUser(PictureGenerateRequestStatus requestStatus, User user);

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(Creator creator);

	List<PictureGenerateRequest> findPendingRequests();

	Optional<PictureGenerateRequest> findById(Long id);

	Optional<PictureGenerateRequest> findByIdAndRequester(Long id, User requester);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);

	Optional<PictureGenerateRequest> findByRequesterAndStatusInOrderByCreatedByDesc(User requester, List<PictureGenerateRequestStatus> statusList);

	Page<PictureGenerateRequest> findAll(Pageable pageable);

}
