package com.gt.genti.application.port.in;

import java.util.List;
import java.util.Optional;

import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;

public interface PictureGenerateRequestPort {

	List<PictureGenerateRequest> findByRequestStatusIsActiveAndUserId_JPQL(Long userId);

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(Long userId);

	List<PictureGenerateRequest> findPendingRequests();

	Optional<PictureGenerateRequest> findById(Long id);

	Optional<PictureGenerateRequest> findByIdAndRequesterId(Long id, Long requesterId);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);
}
