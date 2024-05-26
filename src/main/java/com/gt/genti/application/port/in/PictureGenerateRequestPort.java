package com.gt.genti.application.port.in;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;

public interface PictureGenerateRequestPort {

	List<PictureGenerateRequest> findByRequestStatusAndUserId(PictureGenerateRequestStatus requestStatus, Long userId);

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(Creator creator);

	List<PictureGenerateRequest> findPendingRequests();

	Optional<PictureGenerateRequest> findById(Long id);

	Optional<PictureGenerateRequest> findByIdAndRequesterId(Long id, Long requesterId);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);

	Optional<PictureGenerateRequest> findByUserIdOrderByCreatedByDesc(Long userId);

	List<PictureGenerateRequest> findAll();
}
