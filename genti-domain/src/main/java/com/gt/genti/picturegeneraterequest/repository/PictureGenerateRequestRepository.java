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

	Optional<PictureGenerateRequest> findTop1ByRequesterOrderByIdDesc(User requester);

	Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNull(User requester, Pageable pageable);

	@Query("SELECT p FROM PictureGenerateRequest p WHERE p.requester = :requester AND p.paid IS NOT NULL")
	Page<PictureGenerateRequest> findAllByRequesterAndPaidIsNotNull(User requester, Pageable pageable);

	@Query("select pgr from PictureGenerateRequest pgr "
		+ "where pgr.pictureGenerateRequestStatus = com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus."
		+ "CREATED "
		+ "and pgr.creator is null "
		+ "order by pgr.createdAt desc")
	List<PictureGenerateRequest> findPendingRequests();

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
