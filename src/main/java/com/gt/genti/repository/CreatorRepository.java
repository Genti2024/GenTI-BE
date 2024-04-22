package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.Creator;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

	@Query("select c from Creator c "
		+ "where c.workable = true "
		+ "and c.pictureGenerateRequest.size <3 "
		+ "order by c.id")
	List<Creator> findAllAvailableCreator();

	@Query("select c from Creator c "
		+ "where c.workable = true "
		+ "and c.pictureGenerateRequest.size <3 "
		+ "order by c.id")
	Optional<Creator> findAvailableCreatorOrderById();
}
