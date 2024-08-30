package com.gt.genti.cashout.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.cashout.model.Cashout;
import com.gt.genti.cashout.model.CashoutStatus;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.user.model.User;

@Repository
public interface CashoutRepository extends JpaRepository<Cashout, Long> {
	Page<Cashout> findAllByCreator(Creator creator, Pageable pageable);

	Page<Cashout> findAllByStatus(Pageable pageable, CashoutStatus status);

	Page<Cashout> findAllByCreatedBy(User user, Pageable pageable);

	Page<Cashout> findAllByCreatedByAndStatus(User user, Pageable pageable, CashoutStatus status);

}
