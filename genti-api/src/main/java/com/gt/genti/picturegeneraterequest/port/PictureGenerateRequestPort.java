package com.gt.genti.picturegeneraterequest.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.user.model.User;

public interface PictureGenerateRequestPort {

	Optional<PictureGenerateRequest> findById(Long id);

	PictureGenerateRequest save(PictureGenerateRequest pictureGenerateRequest);

	Page<PictureGenerateResponse> findByPGRESStatusInAndMatchToAdminIsAndPaidIsNull(List<PictureGenerateResponseStatus> statusList,
		boolean matchToAdmin, Pageable pageable);

	Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNull(boolean matchToAdmin, Pageable pageable);

	Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNotNull(boolean matchToAdmin, Pageable pageable);

	Page<PictureGenerateRequest> findAllByRequester(User foundUser, Pageable pageable);

	Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNotNull(User foundUser, Pageable pageable);

	Optional<PictureGenerateRequest> findTopByRequesterOrderByCreatedAtDesc(User foundUser);
}
