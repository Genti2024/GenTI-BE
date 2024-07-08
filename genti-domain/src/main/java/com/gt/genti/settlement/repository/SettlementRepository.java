package com.gt.genti.settlement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.settlement.model.Settlement;
import com.gt.genti.user.model.User;

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
		+ "and s.pictureGenerateResponse.status = com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.COMPLETED "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllWithdrawableByCreatorOrderByCreatedAtDesc(Creator creator);

	@Query("select s "
		+ "from Settlement s "
		+ "where s.pictureGenerateResponse.creator = :creator "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllByCreatorOrderByCreatedAtDesc(Creator creator);

}