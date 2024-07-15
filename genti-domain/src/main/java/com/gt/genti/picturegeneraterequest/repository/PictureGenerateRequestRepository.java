package com.gt.genti.picturegeneraterequest.repository;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;
import com.gt.genti.user.model.User;


@Repository
public interface PictureGenerateRequestRepository
	extends JpaRepository<PictureGenerateRequest, Long> {

	@Query("select pqr "
		+ "from PictureGenerateRequest pqr "
		+ "where pqr.requester = :requester and "
		+ "pqr.pictureGenerateRequestStatus  = :requestStatus "
		+ "order by pqr.createdAt desc")
	List<PictureGenerateRequest> findByRequestStatusAndUser(PictureGenerateRequestStatus requestStatus,
		User requester);

	List<PictureGenerateRequest> findAllByRequester(User requester);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.pictureGenerateRequestStatus = com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus."
		+ "IN_PROGRESS "
		+ "and pgr.creator= :creator "
		+ "order by pgr.createdAt desc "
		+ "limit 1 ")
	Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
		Creator creator);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.pictureGenerateRequestStatus = com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus."
		+ "CREATED "
		+ "and pgr.creator is null "
		+ "order by pgr.createdAt desc")
	List<PictureGenerateRequest> findPendingRequests();

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.id = :id "
		+ "and pgr.requester = :requester ")
	Optional<PictureGenerateRequest> findByIdAndRequesterId(Long id, User requester);

	List<PictureGenerateRequest> findAllByCreatorIsOrderByCreatedAtDesc(Creator creator);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.creator = :creator "
		+ "and pgr.pictureGenerateRequestStatus = :status "
		+ "order by pgr.createdAt desc "
		+ "limit 1 ")
	Optional<PictureGenerateRequest> findByStatusOrderByCreatedAtDesc(Creator creator,
		PictureGenerateRequestStatus status);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.requester = :requester "
		+ "and pgr.pictureGenerateRequestStatus in :statusList "
		+ "order by pgr.createdAt desc "
		+ "limit 1 ")
	Optional<PictureGenerateRequest> findByUserIdAndRequestStatusIn(User requester,
		List<PictureGenerateRequestStatus> statusList);

	//TODO 성능 이슈 때문에 dto를 select하도록 변경해야함
	// edited at 2024-05-23
	// author 서병렬

	@Query("select pgreq from PictureGenerateRequest pgreq "
		+ "join PictureGenerateResponse pgres "
		+ "where pgres.request = pgreq "
		+ "and pgres.status in :activeResponseStatusList "
		+ "and pgreq.creator = :foundCreator "
		+ "and pgreq.pictureGenerateRequestStatus in :activeRequestStatusList "
		+ "order by pgreq.createdAt desc "
		+ "limit 3 ")
	List<PictureGenerateRequest> findByCreatorAndActiveStatus(Creator foundCreator,
		List<PictureGenerateRequestStatus> activeRequestStatusList,
		List<PictureGenerateResponseStatus> activeResponseStatusList);


	Page<PictureGenerateRequest> findAll(@NotNull Pageable pageable);

	@Query("select pgres "
		+ "from PictureGenerateResponse pgres "
		+ "where pgres.request.matchToAdmin = :matchToAdmin "
		+ "and pgres.status in :statusList "
		+ "order by pgres.request.createdAt desc")
	Page<PictureGenerateResponse> findByPictureGenerateResponseStatusInAndMatchToAdminIs(
		List<PictureGenerateResponseStatus> statusList, boolean matchToAdmin, Pageable pageable);


	Page<PictureGenerateRequest> findByMatchToAdminIs(boolean matchToAdmin, Pageable pageable);
}
