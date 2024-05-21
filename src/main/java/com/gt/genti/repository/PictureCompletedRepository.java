package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.PictureCompleted;
import com.gt.genti.domain.PictureCreatedByCreator;
import com.gt.genti.domain.PictureGenerateResponse;
import com.gt.genti.domain.User;

public interface PictureCompletedRepository extends JpaRepository<PictureCompleted, Long> {
	Optional<PictureCompleted> findByUrl(String url);

	// @Query("select pc from PictureCompleted pc "
	// 	+ "where pc.pictureGenerateResponse = :pictureGenerateResponse ")
	Optional<PictureCompleted> findByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);

	@Query("select p "
		+ "from PictureCompleted p "
		+ ", PictureGenerateResponse pgres "
		+ "where pgres.request.requester = :user "
		+ "and p.pictureGenerateResponse = pgres "
		+ "order by p.createdAt desc ")
	List<PictureCompleted> findAllByUser(User user);
}
