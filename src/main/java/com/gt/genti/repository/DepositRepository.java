package com.gt.genti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Deposit;
import com.gt.genti.domain.User;

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
