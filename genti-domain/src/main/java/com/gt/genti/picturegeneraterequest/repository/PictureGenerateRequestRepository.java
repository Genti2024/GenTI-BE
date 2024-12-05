package com.gt.genti.picturegeneraterequest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	List<PictureGenerateRequest> findAllByRequester(User requester);

	Optional<PictureGenerateRequest> findTop1ByRequesterOrderByIdDesc(User requester);

	Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNull(User requester, Pageable pageable);

	@Query("SELECT p FROM PictureGenerateRequest p WHERE p.requester = :requester AND p.paid IS NOT NULL")
	Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNotNull(User requester, Pageable pageable);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.pictureGenerateRequestStatus = com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus."
		+ "IN_PROGRESS "
		+ "and pgr.creator= :creator "
		+ "order by pgr.createdAt desc "
		+ "limit 1 ")
	Optional<PictureGenerateRequest> findByCreatorAndRequestStatusIsBeforeWorkOrderByCreatedAtAsc(
		@Param(value = "creator") Creator creator);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.pictureGenerateRequestStatus = com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus."
		+ "CREATED "
		+ "and pgr.creator is null "
		+ "order by pgr.createdAt desc")
	List<PictureGenerateRequest> findPendingRequests();

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.id = :id "
		+ "and pgr.requester = :requester ")
	Optional<PictureGenerateRequest> findByIdAndRequesterId(@Param(value = "id") Long id, @Param(value = "requester") User requester);

	List<PictureGenerateRequest> findAllByCreatorIsOrderByCreatedAtDesc(Creator creator);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.creator = :creator "
		+ "and pgr.pictureGenerateRequestStatus = :status "
		+ "order by pgr.createdAt desc "
		+ "limit 1 ")
	Optional<PictureGenerateRequest> findByStatusOrderByCreatedAtDesc(
		@Param(value = "creator") Creator creator,
		@Param(value = "status") PictureGenerateRequestStatus status);


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
	List<PictureGenerateRequest> findByCreatorAndActiveStatus(
		@Param(value = "foundCreator")
		Creator foundCreator,
		@Param(value = "activeRequestStatusList")
		List<PictureGenerateRequestStatus> activeRequestStatusList,
		@Param(value = "activeResponseStatusList")
		List<PictureGenerateResponseStatus> activeResponseStatusList);

	// 해당 조인은 pgreq의 paid를 사용하기 위함이다. pgres에서 paid를 참고하면 조인을 안해도 되는 방법이 있을까?
	@Query("select pgres "
		+ "from PictureGenerateResponse pgres "
		+ "join PictureGenerateRequest pgreq "
		+ "where pgres.request = pgreq "
		+ "and pgres.request.matchToAdmin = :matchToAdmin "
		+ "and pgres.status in :statusList "
		+ "and pgreq.paid IS NULL "
		+ "order by pgres.request.createdAt desc")
	Page<PictureGenerateResponse> findByPictureGenerateResponseStatusInAndMatchToAdminIsAndPaidIsNull(
		@Param(value = "statusList")
		List<PictureGenerateResponseStatus> statusList,
		@Param(value = "matchToAdmin")
		boolean matchToAdmin,
		Pageable pageable);

	Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNull(boolean matchToAdmin, Pageable pageable);

	@Query("SELECT p FROM PictureGenerateRequest p WHERE p.paid IS NOT NULL")
	Page<PictureGenerateRequest> findByMatchToAdminIsAndPaidIsNotNull(boolean matchToAdmin, Pageable pageable);

	Optional<PictureGenerateRequest> findTopByRequesterOrderByCreatedAtDesc(User requester);

	List<PictureGenerateRequest> findAllByCreatedAtBeforeAndPictureGenerateRequestStatusIn(LocalDateTime createdAt, List<PictureGenerateRequestStatus> status);
}
