package com.gt.genti.settlement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gt.genti.creator.model.Creator;
import com.gt.genti.settlement.model.Settlement;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

	@Query("select s "
		+ "from Settlement s "
		+ "where s.pictureGenerateResponse.creator = :creator "
		+ "and s.withdrawRequest is null "
		+ "and s.pictureGenerateResponse.status = com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.COMPLETED "
		+ "order by s.createdAt desc ")
	List<Settlement> findAllWithdrawableByCreatorOrderByCreatedAtDesc(
		@Param(value = "creator")
		Creator creator);

}