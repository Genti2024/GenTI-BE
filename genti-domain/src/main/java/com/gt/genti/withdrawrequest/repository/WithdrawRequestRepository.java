package com.gt.genti.withdrawrequest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.withdrawrequest.model.WithdrawRequest;
import com.gt.genti.withdrawrequest.model.WithdrawRequestStatus;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {
	Page<WithdrawRequest> findAllByCreator(Creator creator, Pageable pageable);

	Page<WithdrawRequest> findAllByStatus(Pageable pageable, WithdrawRequestStatus status);
}
