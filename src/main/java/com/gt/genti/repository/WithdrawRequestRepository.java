package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.WithdrawRequest;
import com.gt.genti.domain.enums.WithdrawRequestStatus;

@Repository
public interface WithdrawRequestRepository extends JpaRepository<WithdrawRequest, Long> {
	Page<WithdrawRequest> findAllByCreator(Creator creator, Pageable pageable);

	List<WithdrawRequest> findAllByCreatorOrderByCreatedAtDesc(Creator creator);

	Page<WithdrawRequest> findAllByStatus(Pageable pageable, WithdrawRequestStatus status);
}
