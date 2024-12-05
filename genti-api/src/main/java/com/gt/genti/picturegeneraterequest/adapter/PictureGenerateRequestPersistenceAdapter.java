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
	public Page<PictureGenerateResponse> findByPGRESStatusInAndMatchToAdminIsAndPaidIsNull(
		List<PictureGenerateResponseStatus> statusList, boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestRepository.findByPictureGenerateResponseStatusInAndMatchToAdminIsAndPaidIsNull(statusList,
			matchToAdmin, pageable);
	}

	@Override
	public Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNull(boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestRepository.findByMatchToAdminIsAndPaidIsNull(matchToAdmin, pageable);

	}

	@Override
	public Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNotNull(boolean matchToAdmin, Pageable pageable) {
		return pictureGenerateRequestRepository.findByMatchToAdminIsAndPaidIsNotNull(matchToAdmin, pageable);
	}

	@Override
	public Page<PictureGenerateRequest> findAllByRequester(User foundUser, Pageable pageable) {
		return pictureGenerateRequestRepository.findAllByRequesterAndPaidIsNull(foundUser, pageable);
	}

	@Override
	public Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNotNull(User foundUser, Pageable pageable) {
		return pictureGenerateRequestRepository.findAllByRequesterAndPaidIsNotNull(foundUser, pageable);
	}

	@Override
	public Optional<PictureGenerateRequest> findTopByRequesterOrderByCreatedAtDesc(User foundUser) {
		return pictureGenerateRequestRepository.findTopByRequesterOrderByCreatedAtDesc(foundUser);
	}
}
