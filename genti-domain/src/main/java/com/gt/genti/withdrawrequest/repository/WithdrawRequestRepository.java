package com.gt.genti.withdrawrequest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;
import com.gt.genti.withdrawrequest.model.WithdrawRequest;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {
	Page<WithdrawRequest> findAllByCreator(Creator creator, Pageable pageable);

	List<WithdrawRequest> findAllByCreatorOrderByCreatedAtDesc(Creator creator);

	Page<WithdrawRequest> findAllByStatus(Pageable pageable, WithdrawRequestStatus status);
}
