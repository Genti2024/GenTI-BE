package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.User;

public interface PictureCompletedRepository extends JpaRepository<PictureCompleted, Long> {
	Optional<PictureCompleted> findByKey(String key);

	// @Query("select pc from PictureCompleted pc "
	// 	+ "where pc.pictureGenerateResponse = :pictureGenerateResponse ")
	List<PictureCompleted> findAllByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);

	@Query("select p "
		+ "from PictureCompleted p "
		+ "join PictureGenerateResponse pgres "
		+ "where pgres.request.requester = :user "
		+ "and p.pictureGenerateResponse.id = pgres.id ")
	Page<PictureCompleted> findAllByUserPagination(User user, Pageable pageable);
}
