package com.gt.genti.deposit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.deposit.model.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
	@Query("select d "
		+ "from Deposit d "
		+ "where d.user.creator = :creator ")
	Optional<Deposit> findByCreator(@Param(value= "creator") Creator creator);
}
