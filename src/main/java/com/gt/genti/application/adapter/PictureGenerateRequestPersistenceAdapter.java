package com.gt.genti.application.adapter;

import static com.gt.genti.domain.enums.converter.db.EnumUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public List<PictureGenerateRequest> findByRequestStatusAndUser(PictureGenerateRequestStatus requestStatus,
		User requester) {
		return pictureGenerateRequestRepository.findByRequestStatusAndUserId(requestStatus, requester);
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
	public Optional<PictureGenerateRequest> findByIdAndRequester(Long id, User requester){
		return pictureGenerateRequestRepository.findByIdAndRequesterId(id, requester);
	}

	@Override
	public PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest){
		return pictureGenerateRequestRepository.save(pictureGenerateRequest);
	}

	@Override
	public Optional<PictureGenerateRequest> findByRequesterAndStatusInOrderByCreatedByDesc(User requester, List<PictureGenerateRequestStatus> statusList) {
		return pictureGenerateRequestRepository.findByUserIdAndRequestStatusIn(requester, statusList);
	}

	@Override
	public Page<PictureGenerateRequest> findAll(Pageable pageable) {
		return pictureGenerateRequestRepository.findAll(pageable);
	}
}
