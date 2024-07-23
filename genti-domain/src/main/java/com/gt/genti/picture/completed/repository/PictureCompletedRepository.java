package com.gt.genti.picture.completed.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;

@Repository
public interface PictureCompletedRepository extends JpaRepository<PictureCompleted, Long> {
	List<PictureCompleted> findAllByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);

	@Query("select p "
		+ "from PictureCompleted p "
		+ "join PictureGenerateResponse pgres "
		+ "where pgres.request.requester = :user "
		+ "and p.pictureGenerateResponse.id = pgres.id ")
	Page<PictureCompleted> findAllByUserPagination(@Param(value = "user") User user, Pageable pageable);
}
