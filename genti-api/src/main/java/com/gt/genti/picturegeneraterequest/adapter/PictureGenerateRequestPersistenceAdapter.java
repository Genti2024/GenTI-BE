package com.gt.genti.picturegeneraterequest.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.port.PictureGenerateRequestPort;
import com.gt.genti.picturegeneraterequest.repository.PictureGenerateRequestRepository;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.user.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PictureGenerateRequestPersistenceAdapter implements PictureGenerateRequestPort {
	private final PictureGenerateRequestRepository pictureGenerateRequestRepository;

	@Override
	public List<PictureGenerateRequest> findAllByRequester(User requester) {
		return pictureGenerateRequestRepository.findAllByRequester(requester);
	}

	@Override
	public Optional<PictureGenerateRequest> findById(Long id) {
		return pictureGenerateRequestRepository.findById(id);
	}

	@Override
	public Optional<PictureGenerateRequest> findByIdAndRequester(Long id, User requester) {
		return pictureGenerateRequestRepository.findByIdAndRequesterId(id, requester);
	}

	@Override
	public PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest) {
		return pictureGenerateRequestRepository.save(pictureGenerateRequest);
	}

	@Override
	public Page<PictureGenerateRequest> findAll(Pageable pageable) {
		return pictureGenerateRequestRepository.findAll(pageable);
	}

	@Override
	public Page<PictureGenerateResponse> findByPGRESStatusInAndMatchToAdminIs(
		List<PictureGenerateResponseStatus> statusList, boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestRepository.findByPictureGenerateResponseStatusInAndMatchToAdminIs(statusList,
			matchToAdmin, pageable);
	}

	@Override
	public Page<PictureGenerateRequest> findByMatchToAdminIs(boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestRepository.findByMatchToAdminIs(matchToAdmin, pageable);

	}

	@Override
	public Page<PictureGenerateRequest> findAllByRequester(User foundUser, Pageable pageable) {
		return pictureGenerateRequestRepository.findAllByRequester(foundUser, pageable);
	}

	@Override
	public Optional<PictureGenerateRequest> findTopByRequesterOrderByCreatedAtDesc(User foundUser) {
		return pictureGenerateRequestRepository.findTopByRequesterOrderByCreatedAtDesc(foundUser);
	}
}
