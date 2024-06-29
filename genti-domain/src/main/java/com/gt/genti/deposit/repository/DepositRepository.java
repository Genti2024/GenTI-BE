package com.gt.genti.deposit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.deposit.model.Deposit;
import com.gt.genti.user.model.User;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	@Query("select d "
		+ "from Deposit d "
		+ "where d.user = :user ")
	Optional<Deposit> findByUser(User user);

	@Query("select d "
		+ "from Deposit d "
		+ "where d.user.creator = :creator ")
	Optional<Deposit> findByCreator(Creator creator);

	@Query("select d "
		+ "from Deposit d "
		+ "where d.user.id = :userId ")
	Optional<Deposit> findByUserId(Long userId);
}
