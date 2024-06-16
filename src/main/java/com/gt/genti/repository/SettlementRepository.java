package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.Settlement;
import com.gt.genti.domain.User;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

	@Query("select s "
		+ "from Settlement s "
		+ "where s.pictureGenerateResponse.creator.user = :user "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllByUserOrderByCreatedAtDesc(User user);

	@Query("select s "
		+ "from Settlement s "
		+ "where s.pictureGenerateResponse.creator = :creator "
		+ "and s.withdrawRequest is null "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllWithdrawableByCreatorOrderByCreatedAtDesc(Creator creator);

	@Query("select s "
		+ "from Settlement s "
		+ "where s.pictureGenerateResponse.creator = :creator "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllByCreatorOrderByCreatedAtDesc(Creator creator);

}