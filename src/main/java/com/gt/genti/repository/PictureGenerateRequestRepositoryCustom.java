package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.PictureGenerateRequest;

@Repository
public interface PictureGenerateRequestRepositoryCustom {

	@Query("select pqr "
			+ "from PictureGenerateRequest pqr "
			+ "where pqr.requester.id = :userId and "
			+ "pqr.requestStatus in (com.gt.genti.domain.enums.RequestStatus.BEFORE_WORK, com.gt.genti.domain.enums.RequestStatus.IN_PROGRESS)"
			+ "order by pqr.createdAt desc")
	List<PictureGenerateRequest> findByRequestStatusIsActiveAndUserId_JPQL(Long userId);

}
