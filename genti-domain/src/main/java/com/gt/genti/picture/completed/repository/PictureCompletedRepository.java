package com.gt.genti.picture.completed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.genti.picture.completed.model.PictureCompleted;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;
import com.gt.genti.user.model.User;

@Repository
public interface PictureCompletedRepository extends JpaRepository<PictureCompleted, Long> {
	Optional<PictureCompleted> findByKey(String key);

	List<PictureCompleted> findAllByPictureGenerateResponse(PictureGenerateResponse pictureGenerateResponse);

	@Query("select p "
		+ "from PictureCompleted p "
		+ "join PictureGenerateResponse pgres "
		+ "where pgres.request.requester = :user "
		+ "and p.pictureGenerateResponse.id = pgres.id ")
	Page<PictureCompleted> findAllByUserPagination(User user, Pageable pageable);
}
