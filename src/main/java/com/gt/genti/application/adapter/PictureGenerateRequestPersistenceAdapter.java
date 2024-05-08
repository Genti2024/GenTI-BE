package com.gt.genti.application.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.repository.PictureGenerateRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PictureGenerateRequestPersistenceAdapter implements PictureGenerateRequestPort {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	@Override
	public List<PictureGenerateRequest> findByRequestStatusIsActiveAndUserId_JPQL(Long userId) {
		return pictureGenerateRequestRepository.findByRequestStatusIsActiveAndUserId_JPQL(userId);
	}

	@Override
	public List<PictureGenerateRequest> findAllByRequester(User requester) {
		return pictureGenerateRequestRepository.findAllByRequester(requester);
	}

	@Override
	public Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(Long userId) {
		return pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(userId);
	}

	@Override
	public List<PictureGenerateRequest> findPendingRequests() {
		return pictureGenerateRequestRepository.findPendingRequests();
	}

	@Override
	public Optional<PictureGenerateRequest> findById(Long id) {
		return pictureGenerateRequestRepository.findById(id);
	}

	@Override
	public Optional<PictureGenerateRequest> findByIdAndRequesterId(Long id, Long requesterId){
		return pictureGenerateRequestRepository.findByIdAndRequesterId(id, requesterId);
	}

	@Override
	public PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest){
		return pictureGenerateRequestRepository.save(pictureGenerateRequest);
	}
}
