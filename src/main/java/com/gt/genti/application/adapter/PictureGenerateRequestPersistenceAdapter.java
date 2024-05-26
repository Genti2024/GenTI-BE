package com.gt.genti.application.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gt.genti.application.port.in.PictureGenerateRequestPort;
import com.gt.genti.domain.Creator;
import com.gt.genti.domain.PictureGenerateRequest;
import com.gt.genti.domain.User;
import com.gt.genti.domain.enums.PictureGenerateRequestStatus;
import com.gt.genti.repository.PictureGenerateRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PictureGenerateRequestPersistenceAdapter implements PictureGenerateRequestPort {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;


	@Override
	public List<PictureGenerateRequest> findByRequestStatusAndUserId(PictureGenerateRequestStatus requestStatus,
		Long userId) {
		return pictureGenerateRequestRepository.findByRequestStatusAndUserId(requestStatus, userId);
	}

	@Override
	public List<PictureGenerateRequest> findAllByRequester(User requester) {
		return pictureGenerateRequestRepository.findAllByRequester(requester);
	}

	@Override
	public Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(Creator creator) {

		return pictureGenerateRequestRepository.findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(creator);
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

	@Override
	public Optional<PictureGenerateRequest> findByUserIdOrderByCreatedByDesc(Long requesterId) {

		List<PictureGenerateRequestStatus> activeStatusList = new ArrayList<>();
		activeStatusList.add(PictureGenerateRequestStatus.CREATED);
		activeStatusList.add(PictureGenerateRequestStatus.ASSIGNING);
		activeStatusList.add(PictureGenerateRequestStatus.IN_PROGRESS);
		return pictureGenerateRequestRepository.findByUserIdAndRequestStatusIn(requesterId, activeStatusList);
	}

	@Override
	public List<PictureGenerateRequest> findAll() {
		return pictureGenerateRequestRepository.findAll();
	}
}
