package com.gt.genti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.Creator;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

	@Query("select c from Creator c "
		+ "join c.pictureGenerateRequest pgr "
		+ "where c.workable = true "
		+ "group by c "
		+ "having count(pgr) < 3 "
		+ "order by count(pgr) asc, c.id asc ")
	List<Creator> findAllAvailableCreator();
}